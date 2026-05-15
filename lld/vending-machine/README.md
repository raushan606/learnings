# Vending Machine LLD

// TODO: Complete Code

1. Clarifying Requirements
    1. Functional Requirements:
        1. only one transaction at a time
        2. coin-based payment mode - 1,5,10;
        3. return excess change amount to customer
        4. Support admin operations – Restock items
        5. No need to maintain transaction history, only the current state
        6. No need to generate receipts
    2. Non-Functional Requirements:
        1. Maintainable
        2. Atomicity
        3. Concurrency Control
        4. Extensibility
2. Identifying core Entities
    1. VendingMachine
    2. Item
    3. Transaction
    4. Coin
    5. Stock
    6. Dispenser
    7. OrderState
    8. Order
3. Class Design
    1. Item (Class): id, name, quantity, price
    2. Coin (Enum) : 1,5,10
    3. PaymentStrategy (Interface)
    4. CoinPaymentStrategy (Class) : implements PaymentStrategy
    5. OrderState (Interface)
    6. IdleState (Class) : implements OrderState
    7. ProcessingState (Class) : implements OrderState
    8. DispensingState (Class) : implements OrderState
    9. Order (Class): id, item, quantity, totalAmount, state
    10. VendingMachine (Class): items, currentOrder, paymentStrategy
    11. Stock (Class): item, quantity
    12. Dispenser (Class): dispense
    13. Classes and Relationships
        1. VendingMachine has a Stock object and Dispenser object
        2. VendingMachine uses PaymentStrategy for processing payments
        3. Order has a state that can be IdleState, ProcessingState, or DispensingState
4. Sequence of Operations
    1. User selects an item and quantity
    2. VendingMachine creates an Order and sets state to ProcessingState
    3. User inserts coins, CoinPaymentStrategy processes the payment
    4. If payment is successful, Order state changes to DispensingState
    5. Dispenser dispenses the item and returns any excess change
    6. Order state changes back to IdleState
5. Concurrency Control
    1. Use synchronized methods or locks to ensure that only one transaction is processed at a time
    2. Handle concurrent access to shared resources like stock and dispenser
6. Extensibility
    1. To add new payment methods, create new classes that implement the PaymentStrategy interface
    2. To add new item types, simply create new Item instances and add them to the VendingMachine's stock
    3. To support multiple transactions, consider implementing a queue system to manage incoming orders and process them sequentially
7. Conclusion
    1. The designed vending machine system meets the specified functional and non-functional requirements while maintaining
          


