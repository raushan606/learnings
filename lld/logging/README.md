# Logging Framework

1. Clarifying Requirements
- Implement a logging framework to log messages with different severity levels (INFO, DEBUG, ERROR, WARN).
- Support logging to multiple outputs: console, file, and remote server.
- Thread-safe logging to handle concurrent log requests.
- Asynchronous logging to avoid blocking the main application flow.
- Filter logs based on severity minimum level.
- Configure the logger by specifying log level, formatters, and appenders.
- Thread-Safety
- Performance
- Extensibility
- Maintainability
- Ease of Use

2. Identifying Core Entities
- LogLevel (Enum): Represents different logging levels (INFO, DEBUG, ERROR, WARN).
- LogMessage: Represents a log message with attributes like timestamp, level, message, and optional
- Logger: Main class responsible for logging messages.
- LogAppender (Interface): Interface for different log appenders (ConsoleAppender, FileAppender, RemoteServerAppender).
- LogFormatter (Interface): Interface for formatting log messages (SimpleFormatter, JSONFormatter).
- AsyncLogger: A logger that handles logging asynchronously.
- LogManager: Responsible for initializing and managing loggers.

3. Designing Classes and Relationships
- Class Definitions
- Key Design Patterns
  - Strategy Pattern: Formatting Strategy
  - Appending Strategy: Different Appenders
  - Singleton Pattern: LogManager
