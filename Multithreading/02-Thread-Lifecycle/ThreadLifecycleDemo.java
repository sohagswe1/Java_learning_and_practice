public class ThreadLifecycleDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            try {
                Thread.sleep(400); // TIMED_WAITING
                System.out.println("Worker task running");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        System.out.println("Before start: " + worker.getState()); // NEW
        worker.start();
        Thread.sleep(100);
        System.out.println("During sleep: " + worker.getState()); // Usually TIMED_WAITING
        worker.join();
        System.out.println("After completion: " + worker.getState()); // TERMINATED
    }
}
