public class VolatileIsNotAtomic {
    private static volatile int count;

    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            for (int i = 0; i < 100_000; i++) count++; // Read-modify-write race.
        };
        Thread a = new Thread(task);
        Thread b = new Thread(task);
        a.start(); b.start(); a.join(); b.join();
        System.out.println("Expected: 200000, actual: " + count);
    }
}
