# Design ATM

### 1. Clarifying Requirements

- Cash Withdrawal
- Deposit
- Balance Inquiry
- Validate and Authenticate User Card Details
- Only One Transaction per session.
- Card eject after each Transaction
- User have only one account. But can have multiple cards.
- ATM maintain inventory for cash notes of different denominations.
- ATM should be able to handle insufficient funds in an account and insufficient cash in ATM scenarios.
- Priority denominations should be handled first.
- No limits on the number and number of transactions per day.
- Modularity
- Extensibility
- Maintainability
- Atomicity
- Consistency

### 2. Identifying Core Entities
- Card
- Account
- BankService
- ATMMachine
- NoteDispenser

### 3. Class Design
- Enums - OperationType
- Data Classes - Card, Account, 
- Core Classes - ATMSystem, BankService
- ATM has BankService
- CashDispenser has a DispenseChain
- NoteDispenser has a DispenseChain

### 4. Key Design Patterns
- State Pattern: ATMState - IdleState, AuthenticatedState, HasCardState
- Singleton Pattern
- Facade Pattern

