public class WaitNotifyDemo {
    private static final Object LOCK = new Object();
    private static boolean ready = false;

    public static void main(String[] args) throws InterruptedException {
        Thread consumer = new Thread(() -> {
            synchronized (LOCK) {
                while (!ready) {
                    try { LOCK.wait(); }
                    catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                System.out.println("Consumer received notification");
            }
        });

        consumer.start();
        Thread.sleep(200);
        synchronized (LOCK) {
            ready = true;
            LOCK.notifyAll();
        }
        consumer.join();
    }
}
