# 04 - Interface Segregation Principle (ISP)

**Definition:** Clients should not be forced to depend on methods they do not use.

Prefer small, role-specific interfaces instead of one large interface that requires every implementation to provide unrelated methods.

## Files

- `Printer.java`: printing capability.
- `Scanner.java`: scanning capability.
- `BasicPrinter.java`: implements only printing.
- `MultiFunctionPrinter.java`: implements both printing and scanning.
- `Main.java`: demonstrates both devices.

## Key benefit

A basic printer is not forced to implement a meaningless scan operation.
