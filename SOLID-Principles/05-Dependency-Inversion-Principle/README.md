# 05 - Dependency Inversion Principle (DIP)

**Definition:**
- High-level modules should not depend on low-level modules. Both should depend on abstractions.
- Abstractions should not depend on details. Details should depend on abstractions.

Use constructor injection to provide an abstraction to a high-level class instead of creating a concrete dependency inside it.

## Files

- `MessageSender.java`: abstraction.
- `EmailSender.java`: one implementation.
- `SmsSender.java`: another implementation.
- `NotificationService.java`: high-level class depends on the abstraction.
- `Main.java`: injects an implementation.

## Key benefit

The notification service can use a different sender without changing its own code.
