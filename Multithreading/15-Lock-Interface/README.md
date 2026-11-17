# 15 - Lock Interface

The `Lock` API offers explicit lock/unlock control. Always release a lock in `finally`. `tryLock()` can avoid waiting indefinitely. `ReadWriteLock` separates read and write locks.
