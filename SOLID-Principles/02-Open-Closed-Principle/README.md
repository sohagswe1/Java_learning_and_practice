# 02 - Open/Closed Principle (OCP)

**Definition:** Software entities should be open for extension but closed for modification.

Add new behavior by introducing new implementations rather than repeatedly modifying stable existing code.

## Files

- `Discount.java`: abstraction for a discount.
- `RegularDiscount.java`: regular customer discount.
- `PremiumDiscount.java`: premium customer discount.
- `DiscountCalculator.java`: works with the abstraction.
- `Main.java`: demonstrates both implementations.

## Key benefit

A new discount type can be added as another `Discount` implementation without changing `DiscountCalculator`.
