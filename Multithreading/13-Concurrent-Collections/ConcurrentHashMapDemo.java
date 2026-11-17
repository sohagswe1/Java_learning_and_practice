import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapDemo {
    public static void main(String[] args) throws InterruptedException {
        ConcurrentHashMap<String, Integer> counts = new ConcurrentHashMap<>();
        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) counts.merge("java", 1, Integer::sum);
        };

        Thread a = new Thread(task);
        Thread b = new Thread(task);
        a.start(); b.start(); a.join(); b.join();
        System.out.println(counts);
    }
}
