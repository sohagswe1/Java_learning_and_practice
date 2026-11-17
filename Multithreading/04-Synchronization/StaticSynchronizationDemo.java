public class StaticSynchronizationDemo {
    private static int total;

    static synchronized void addOne() {
        total++;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread a = new Thread(() -> { for (int i = 0; i < 50_000; i++) addOne(); });
        Thread b = new Thread(() -> { for (int i = 0; i < 50_000; i++) addOne(); });
        a.start(); b.start(); a.join(); b.join();
        System.out.println("Total: " + total);
        System.out.println("Static synchronized locks StaticSynchronizationDemo.class");
    }
}
