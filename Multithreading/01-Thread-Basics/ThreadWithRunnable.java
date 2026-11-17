public class ThreadWithRunnable {
    public static void main(String[] args) {
        Runnable task = () -> System.out.println(
            "Running on: " + Thread.currentThread().getName()
        );
        Thread worker = new Thread(task, "worker-1");
        worker.start();
    }
}
