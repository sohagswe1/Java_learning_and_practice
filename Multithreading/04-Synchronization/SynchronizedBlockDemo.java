public class SynchronizedBlockDemo {
    private static int balance = 0;
    private static final Object LOCK = new Object();

    private static void deposit(int amount) {
        synchronized (LOCK) {
            balance += amount;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread a = new Thread(() -> { for (int i = 0; i < 1000; i++) deposit(10); });
        Thread b = new Thread(() -> { for (int i = 0; i < 1000; i++) deposit(10); });
        a.start(); b.start(); a.join(); b.join();
        System.out.println("Balance: " + balance);
    }
}
