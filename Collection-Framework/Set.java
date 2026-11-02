
import java.util.*;

public class Set {
    public static void main(String[] args) {

        Set<Integer> set = new HashSet<>();

        // 1. Add
        set.add(10);
        set.add(20);
        set.add(30);
        set.add(10);

        System.out.println(set);

        // 2. Contains
        System.out.println(set.contains(20));

        // 3. Size
        System.out.println(set.size());

        // 4. Remove
        set.remove(10);

        // 5. Is Empty
        System.out.println(set.isEmpty());

        // 6. Add All
        Set<Integer> set2 = new HashSet<>();
        set2.add(40);
        set2.add(50);

        set.addAll(set2);

        // 7. Contains All
        System.out.println(set.containsAll(set2));

        // 8. Remove All
        set.removeAll(set2);

        // 9. Retain All
        set.addAll(set2);
        set.retainAll(set2);

        // 10. Iterator
        Iterator<Integer> it = set.iterator();

        while (it.hasNext()) {
            System.out.println(it.next());
        }

        // 11. For Each
        set.forEach(System.out::println);

        // 12. Remove If
        set.removeIf(x -> x > 40);

        // 13. To Array
        Object[] arr = set.toArray();

        // 14. Clear
        set.clear();

        System.out.println(set);
    }
}
