
import java.util.*;

public class ArrayListMethods {

    public static void main(String[] args) {

        ArrayList<Integer> list = new ArrayList<>();

        // 1. ADD
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(1, 15);
        list.addAll(Arrays.asList(40, 50));
        list.addAll(1, Arrays.asList(5, 6));
        System.out.println(list);

        // 2. ACCESS AND UPDATE
        System.out.println(list.get(0));
        list.set(0, 100);
        System.out.println(list);

        // 3. SEARCH
        System.out.println(list.contains(20));
        System.out.println(list.indexOf(20));
        System.out.println(list.lastIndexOf(20));
        System.out.println(list.containsAll(Arrays.asList(20, 30)));

        // 4. SIZE AND STATUS
        System.out.println(list.size());
        System.out.println(list.isEmpty());

        // 5. REMOVE
        list.remove(0);                       // By index
        list.remove(Integer.valueOf(20));     // By value
        list.removeAll(Arrays.asList(30, 40));
        list.removeIf(x -> x > 100);
        System.out.println(list);

        // 6. RETAIN AND CLEAR
        ArrayList<Integer> nums =
                new ArrayList<>(Arrays.asList(10, 20, 30, 40));

        nums.retainAll(Arrays.asList(20, 40));
        System.out.println(nums);

        nums.clear();
        System.out.println(nums);

        // 7. LOOP
        ArrayList<Integer> arr =
                new ArrayList<>(Arrays.asList(10, 20, 30));

        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(i));
        }

        for (int x : arr) {
            System.out.println(x);
        }

        // 8. ITERATOR
        Iterator<Integer> it = arr.iterator();

        while (it.hasNext()) {
            System.out.println(it.next());
        }

        // 9. LIST ITERATOR
        ListIterator<Integer> li = arr.listIterator();

        while (li.hasNext()) {
            System.out.println(li.next());
        }

        while (li.hasPrevious()) {
            System.out.println(li.previous());
        }

        // 10. SORTING
        Collections.sort(arr);
        System.out.println(arr);

        Collections.sort(arr, Collections.reverseOrder());
        System.out.println(arr);

        // 11. REVERSE, SWAP, SHUFFLE
        Collections.reverse(arr);
        Collections.swap(arr, 0, 1);
        Collections.shuffle(arr);
        System.out.println(arr);

        // 12. MIN, MAX, FREQUENCY
        ArrayList<Integer> numbers =
                new ArrayList<>(Arrays.asList(10, 20, 20, 30));

        System.out.println(Collections.min(numbers));
        System.out.println(Collections.max(numbers));
        System.out.println(Collections.frequency(numbers, 20));

        // 13. SUBLIST
        System.out.println(numbers.subList(1, 3));

        // 14. ARRAY CONVERSION
        Object[] a = numbers.toArray();
        Integer[] b = numbers.toArray(new Integer[0]);

        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(b));

        // 15. REPLACE ALL
        numbers.replaceAll(x -> x * 2);
        System.out.println(numbers);

        // 16. REMOVE RANGE USING SUBLIST
        ArrayList<Integer> values =
                new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));

        values.subList(1, 3).clear();
        System.out.println(values); // [1, 4, 5]

        // 17. CAPACITY METHODS
        ArrayList<Integer> capacityList = new ArrayList<>();
        capacityList.ensureCapacity(100);
        capacityList.add(10);
        capacityList.trimToSize();

        // 18. CLONE
        ArrayList<Integer> copy =
                (ArrayList<Integer>) numbers.clone();

        System.out.println(copy);
    }
}
