import java.util.*;

public class Main {
    public static void main(String[] args) {

        Queue<Integer> q = new LinkedList<>();

        // Add elements
        q.add(10);
        q.offer(20);
        q.offer(30);

        // Print queue
        System.out.println(q);

        // First element
        System.out.println(q.peek());
        System.out.println(q.element());

        // Remove first element
        q.remove();
        q.poll();

        // Remove specific element
        q.remove(30);

        // Check element
        System.out.println(q.contains(20));

        // Size
        System.out.println(q.size());

        // Empty check
        System.out.println(q.isEmpty());

        // Iterate using for-each
        for (int x : q) {
            System.out.println(x);
        }

        // Iterator
        Iterator<Integer> it = q.iterator();

        while (it.hasNext()) {
            System.out.println(it.next());
        }

        // Convert Queue to Array
        Object[] arr = q.toArray();

        // Clear all elements
        q.clear();

        // Check empty
        System.out.println(q.isEmpty());
    }
}