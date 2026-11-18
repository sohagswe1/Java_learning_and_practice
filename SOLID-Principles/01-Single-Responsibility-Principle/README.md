# 01 - Single Responsibility Principle (SRP)

**Definition:** A class should have one reason to change.

Keep each class focused on one responsibility. For example, invoice calculation, invoice printing, and invoice persistence should not all be handled by one class.

## Files

- `Invoice.java`: represents invoice data and calculates its total.
- `InvoicePrinter.java`: handles printing.
- `InvoiceRepository.java`: handles persistence responsibility (demonstration).
- `Main.java`: runs the example.

## Key benefit

Changes to printing or persistence do not require changing the invoice calculation class.
