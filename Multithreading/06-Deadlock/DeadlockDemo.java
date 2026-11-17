public class DeadlockDemo {
    private static final Object A = new Object();
    private static final Object B = new Object();

    public static void main(String[] args) {
        Thread first = new Thread(() -> {
            synchronized (A) {
                System.out.println("Thread-1 acquired A");
                pause();
                synchronized (B) { System.out.println("Thread-1 acquired B"); }
            }
        });

        Thread second = new Thread(() -> {
            synchronized (B) {
                System.out.println("Thread-2 acquired B");
                pause();
                synchronized (A) { System.out.println("Thread-2 acquired A"); }
            }
        });

        first.start();
        second.start();
    }

    private static void pause() {
        try { Thread.sleep(100); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
