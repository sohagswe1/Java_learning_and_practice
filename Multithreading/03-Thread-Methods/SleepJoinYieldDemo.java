public class SleepJoinYieldDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                System.out.println(Thread.currentThread().getName() + ": " + i);
                Thread.yield(); // Hint only; scheduler may ignore it.
                try { Thread.sleep(200); }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }, "worker");

        worker.start();
        worker.join();
        System.out.println("Worker completed; main continues");
    }
}
