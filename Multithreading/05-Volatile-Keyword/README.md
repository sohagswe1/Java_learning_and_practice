# 05 - Volatile Keyword

`volatile` provides visibility guarantees between threads for reads and writes of that variable.

It does not make compound operations such as `count++` atomic. Use `synchronized` or `AtomicInteger` for atomic increments.
