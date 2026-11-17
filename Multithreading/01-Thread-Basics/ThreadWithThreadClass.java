public class ThreadWithThreadClass {
    public static void main(String[] args) {
        Thread worker = new Thread("worker-2") {
            @Override
            public void run() {
                System.out.println("Running on: " + Thread.currentThread().getName());
            }
        };
        worker.start();
    }
}
