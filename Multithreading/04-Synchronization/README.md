# 04 - Synchronization

Synchronization protects shared mutable state with an object's monitor.

- `RaceConditionDemo.java`: unsafe counter; result may be lower than expected.
- `SynchronizedMethodDemo.java`: protects increment and read methods.
- `SynchronizedBlockDemo.java`: protects a critical section.
- `StaticSynchronizationDemo.java`: static synchronized method locks the Class object.

All threads accessing the same state must coordinate using the same lock.
