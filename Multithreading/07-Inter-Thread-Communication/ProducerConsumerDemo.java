import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumerDemo {
    private static final Queue<Integer> queue = new LinkedList<>();
    private static final int CAPACITY = 3;

    public static void main(String[] args) throws InterruptedException {
        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                synchronized (queue) {
                    while (queue.size() == CAPACITY) {
                        try { queue.wait(); }
                        catch (InterruptedException e) { Thread.currentThread().interrupt(); return; }
                    }
                    queue.add(i);
                    System.out.println("Produced " + i);
                    queue.notifyAll();
                }
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try { queue.wait(); }
                        catch (InterruptedException e) { Thread.currentThread().interrupt(); return; }
                    }
                    System.out.println("Consumed " + queue.remove());
                    queue.notifyAll();
                }
            }
        });

        producer.start(); consumer.start();
        producer.join(); consumer.join();
    }
}
