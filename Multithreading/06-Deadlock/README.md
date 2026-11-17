# 06 - Deadlock

Deadlock happens when threads wait forever for locks held by one another.

- `DeadlockDemo.java` intentionally creates a deadlock. Stop it with Ctrl+C.
- `DeadlockPreventionByLockOrdering.java` avoids circular wait by acquiring locks in a consistent order.

Four Coffman conditions: mutual exclusion, hold-and-wait, no preemption, and circular wait.
