public class RaceConditionDemo {
    static class Counter {
        int count;
        void increment() { count++; } // Not atomic.
    }

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        Thread[] threads = new Thread[4];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 100_000; j++) counter.increment();
            });
            threads[i].start();
        }
        for (Thread t : threads) t.join();
        System.out.println("Expected: 400000, actual: " + counter.count);
    }
}
