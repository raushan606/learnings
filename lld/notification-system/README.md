# Notification System — LLD Practice

## Problem Statement

Design a notification system that can send notifications to users through multiple channels (Email, SMS, Push). The system should be extensible to add new channels, resilient to transient failures, and capable of handling high throughput without blocking the caller.

---

## Requirements

### Functional Requirements
- Send notifications via Email, SMS, and Push channels
- Each notification has a recipient, a type, a message, and an optional subject
- A recipient may have any combination of contact fields (email, phone, push token)
- The system must retry delivery on failure before giving up

### Non-Functional Requirements
- Non-blocking: callers should not wait for delivery to complete
- Resilient: transient failures must be retried automatically
- Extensible: adding a new channel must not require modifying existing code
- Performant: support concurrent notification delivery via a thread pool

---

## Core Entities

### `Recipient`
Represents the user receiving a notification. Contact fields are optional because a user may only be reachable on some channels.

```
Recipient
├── userId        : String
├── email         : Optional<String>
├── phoneNumber   : Optional<String>
└── pushToken     : Optional<String>
```

### `Notification`
The notification payload. Built using the **Builder pattern** to avoid a bloated constructor as fields grow.

```
Notification
├── id        : String        (auto UUID)
├── recipient : Recipient
├── type      : NotificationType
├── message   : String
└── subject   : String        (optional — used by Email and Push)
```

### `NotificationType` (Enum)
```
EMAIL | SMS | PUSH
```

---

## Design Decisions

### Why Builder for `Notification`?
`Notification` has required fields (recipient, type) and optional fields (subject). A constructor with all four parameters forces callers to pass `null` for unused fields. Builder makes construction explicit and readable — the compiler enforces required fields, and optional fields are set only when needed.

### Why Strategy for gateways?
Each channel has a completely different delivery mechanism. Encoding all three inside a single class with `if/else` blocks makes the class hard to test and impossible to extend without modification. Strategy isolates each channel behind a common interface — `NotificationGateway.send()` — so they can be tested and deployed independently.

### Why Factory for gateway creation?
The service needs to pick the right gateway at runtime based on `NotificationType`. A factory centralises this mapping and caches instances so gateways (which are stateless) are not re-created on every send. Without a factory, the mapping logic bleeds into the service.

### Why Decorator for retry?
Retry is a cross-cutting concern. Duplicating retry logic inside every gateway violates DRY. Putting it in the service mixes two responsibilities. A Decorator wraps any `NotificationGateway` and adds retry transparently — the gateway doesn't know it's being retried, and the service doesn't know how retry works.

### Why Facade for `NotificationService`?
Callers should not need to know about factories, decorators, or thread pools. The service is the single entry point. It wires everything together internally and exposes one method: `sendNotification(Notification)`.

### Why `ExecutorService` (async)?
Notification delivery is I/O-bound and may take hundreds of milliseconds. Blocking the calling thread for each send would make the system unusable under load. A fixed thread pool lets the service accept notifications quickly and deliver them concurrently.

---

## Class Diagram

```
            ┌──────────────────────────┐
            │     NotificationService   │   ← Facade
            │  - executorService        │
            │  + sendNotification()     │
            │  + shutdown()             │
            └────────────┬─────────────┘
                         │ uses
                         ▼
            ┌──────────────────────────┐
            │    NotificationFactory    │   ← Factory
            │  - gatewayMapping (cache) │
            │  + createGateway(type)    │
            └────────────┬─────────────┘
                         │ creates
                         ▼
       ┌─────────────────────────────────────┐
       │        <<interface>>                │
       │       NotificationGateway           │   ← Strategy
       │  + send(Notification) throws Ex     │
       └──────┬──────────────────────┬───────┘
              │                      │
    ┌─────────▼──────────┐   ┌───────▼────────────────────────┐
    │  EmailGateway       │   │   RetryableGatewayDecorator     │  ← Decorator
    │  SmsGateway         │   │  - gateway : NotificationGateway│
    │  PushGateway        │   │  - maxRetries : int             │
    └────────────────────┘   │  - retryDelayMillis : long      │
                              │  + send(Notification)           │
                              └─────────────────────────────────┘

            ┌──────────────────────────┐
            │       Notification        │
            │  - id                     │
            │  - recipient : Recipient  │
            │  - type : NotificationType│
            │  - message                │
            │  - subject                │
            │                           │
            │  + Builder (inner class)  │   ← Builder
            └──────────────────────────┘

            ┌──────────────────────────┐
            │        Recipient          │
            │  - userId                 │
            │  - email    : Optional    │
            │  - phone    : Optional    │
            │  - pushToken: Optional    │
            └──────────────────────────┘
```

---

## Request Flow

```
Client
  │
  │  sendNotification(notification)
  ▼
NotificationService
  │  submit to ExecutorService (returns immediately)
  │
  │  [on worker thread]
  │  NotificationFactory.createGateway(type)  →  EmailGateway / SmsGateway / PushGateway
  │  new RetryableGatewayDecorator(gateway, maxRetries=3, delay=1000ms)
  │  decorator.send(notification)
  │
  ├── Attempt 1 → gateway.send()
  │       ├── success → done
  │       └── failure → sleep 1000ms
  │
  ├── Attempt 2 → gateway.send()
  │       ├── success → done
  │       └── failure → sleep 1000ms
  │
  └── Attempt 3 → gateway.send()
          ├── success → done
          └── failure → log error, give up
```

---

## Project Structure

```
notification-system/
└── src/main/java/com/raushan/
    ├── NotificationService.java             # Facade
    ├── NotificationSystemDemo.java          # Usage demo
    ├── entities/
    │   ├── Notification.java                # Data model + Builder
    │   └── Recipient.java                   # Recipient with Optional contacts
    ├── enums/
    │   └── NotificationType.java            # EMAIL | SMS | PUSH
    ├── strategy/
    │   ├── NotificationGateway.java         # Strategy interface
    │   ├── EmailGateway.java
    │   ├── SmsGateway.java
    │   └── PushGateway.java
    ├── factory/
    │   └── NotificationFactory.java         # Gateway creation + caching
    └── decorator/
        └── RetryableGatewayDecorator.java   # Retry wrapper
```

---

## Design Patterns Summary

| Pattern | Where Used | Why |
|---|---|---|
| **Builder** | `Notification` | Required + optional fields without telescoping constructors |
| **Strategy** | `NotificationGateway` + impls | Pluggable channel delivery, open for extension |
| **Factory** | `NotificationFactory` | Decouple gateway selection from service logic |
| **Decorator** | `RetryableGatewayDecorator` | Add retry to any gateway without changing it |
| **Facade** | `NotificationService` | Single entry point hiding factory, decorator, and thread pool |

---

## How to Extend: Add a New Channel

Scenario: add WhatsApp notifications. Touch only 3 things — no existing class changes.

**1. Add enum value**
```java
// NotificationType.java
public enum NotificationType { EMAIL, SMS, PUSH, WHATSAPP }
```

**2. Implement the gateway**
```java
public class WhatsAppGateway implements NotificationGateway {
    @Override
    public void send(Notification notification) throws Exception {
        String phone = notification.getRecipient().getPhoneNumber()
            .orElseThrow(() -> new IllegalArgumentException("No phone for WhatsApp"));
        System.out.println("[WhatsApp] To: " + phone + " | " + notification.getMessage());
    }
}
```

**3. Register in factory**
```java
case WHATSAPP -> new WhatsAppGateway();
```

`NotificationService`, `RetryableGatewayDecorator`, and all existing gateways are untouched. This is the **Open/Closed Principle** in action.

---

## Usage

```java
NotificationService service = new NotificationService(10);

Recipient user = new Recipient(
    "user123",
    Optional.of("user@example.com"),
    Optional.of("+15551234567"),
    Optional.of("push-token-xyz")
);

// Email
service.sendNotification(
    new Notification.Builder(user, NotificationType.EMAIL)
        .subject("Welcome!")
        .message("Thanks for signing up.")
        .build()
);

// SMS
service.sendNotification(
    new Notification.Builder(user, NotificationType.SMS)
        .message("Your OTP is 482910")
        .build()
);

// Push
service.sendNotification(
    new Notification.Builder(user, NotificationType.PUSH)
        .subject("New message")
        .message("Jane sent you a message.")
        .build()
);

service.shutdown();
```