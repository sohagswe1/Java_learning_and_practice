public class StartVsRun {
    public static void main(String[] args) {
        Thread thread = new Thread(
            () -> System.out.println("Executed by: " + Thread.currentThread().getName()),
            "new-worker"
        );
        thread.run();   // Ordinary method call: executes on main.
        thread.start(); // Starts a separate thread.
    }
}
