# Movie Booking System

- Functional Requirements
    - Search for a movie title and location.
    - Support multiple locations, cinems, screens, and shows.
    - Different types of seats
    - Book one or more tickets.
    - Double booking should not be allowed.
    - Price should be calculated dynamically based on seats.
    - Users can subscribes to movies and receive notifications when booking opens for them.
    - Support different payment methods.
- Non-Functional Requirements
    - Concurrency.
    - Extensibility.
    - Modularity
    - Simplified Interface.
- Identifying Core Entities
    - City: id, name
    - Cinema: id, name, city, screens
    - Screen: id, seats, addSeat(Seat): void
    - Seat: id, row, col, status, type
    - Movie: id, title, duration
    - Show: id, movie, screen, startTime, pricingStrategy
    - User: id, name, email
    - Booking: id, user, show, seats, totalAmount, payment, confirmBooking(): void
    - SeatLockManager: LOCK_TIMEOUT_MS: long, lockedSeats: Map<Show, Map<Seat, String>>, scheduler: ScheduledExecutorService, lockSeats(Show,
      List<Seat>, String): Void, unlockSeats(Show, List<Seat>, String): void, shutdown():void
    - PaymentStrategy
    - PricingStrategy
    - Payment: id, amount, status, transactionId
    - BookingManager: seatLockManager, createBooking(User, Show, List<Seat>, PaymentStrategy): Optional<Booking>
    - MovieBookingSystem: instance, users, movies, cinemas, cities, shows, seatLockManager, bookingManager, addMovie(Movie),findShows(movie, city)
      , findCinemForShow(show), getInstance(), addShow(Show), bookTickets(), createUser, addCity, addCinema, getBookingManager, shutdown()
    - SeatType [REGULAR, RECLINIER, PREMIUM]
    - SeatStatus [AVAILABLE,LOCKED,BOOKED]
    - PaymentStatus [PENDING, SUCCESS, FAILURE]
- Design Patterns:
  - User Strategy pattern for Pricing and Payment strategies.
  - User Observer pattern to notify users when booking opens for a movie which they subscribed to.