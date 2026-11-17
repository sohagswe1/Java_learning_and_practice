public class BlockedStateDemo {
    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread first = new Thread(() -> {
            synchronized (LOCK) {
                try { Thread.sleep(500); }
                catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
        });

        Thread second = new Thread(() -> {
            synchronized (LOCK) {
                System.out.println("Second thread acquired lock");
            }
        });

        first.start();
        Thread.sleep(50);
        second.start();
        Thread.sleep(100);
        System.out.println("Second thread state: " + second.getState()); // Usually BLOCKED
        first.join();
        second.join();
    }
}
