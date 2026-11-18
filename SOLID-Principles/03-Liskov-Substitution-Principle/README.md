# 03 - Liskov Substitution Principle (LSP)

**Definition:** Objects of a superclass should be replaceable with objects of its subclasses without breaking the correctness of the program.

Subtypes must honor the behavior promised by their base type. Avoid inheritance when a subtype cannot safely support the base type's contract.

## Files

- `Bird.java`: base abstraction for birds.
- `FlyingBird.java`: abstraction for birds that can fly.
- `Sparrow.java`: a flying bird.
- `Penguin.java`: a bird that cannot fly.
- `Main.java`: demonstrates substitutable use of the abstractions.

## Key benefit

Code that requires a `FlyingBird` can safely use any subtype that fulfills the flying contract. A penguin is not incorrectly forced into that contract.
