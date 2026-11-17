public class VolatileVisibilityDemo {
    private static volatile boolean running = true;

    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            while (running) {
                // Repeatedly reads the volatile flag.
            }
            System.out.println("Worker observed the stop signal");
        });

        worker.start();
        Thread.sleep(300);
        running = false;
        worker.join();
    }
}
