public class InterruptDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("Working...");
                    Thread.sleep(150);
                }
            } catch (InterruptedException e) {
                System.out.println("Sleep interrupted; stopping cooperatively");
                Thread.currentThread().interrupt();
            }
        });

        worker.start();
        Thread.sleep(450);
        worker.interrupt();
        worker.join();
    }
}
