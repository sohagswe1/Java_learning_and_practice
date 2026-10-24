
import java.util.*;

public class MapMethods {

    public static void main(String[] args) {

        Map<String, Integer> map = new HashMap<>();

        // 1. Add elements
        map.put("A", 10);
        map.put("B", 20);
        map.put("C", 30);
        map.putIfAbsent("D", 40);
        map.putAll(Map.of("E", 50, "F", 60));

        // 2. Get elements
        System.out.println(map.get("A"));
        System.out.println(map.getOrDefault("X", 0));

        // 3. Search
        System.out.println(map.containsKey("A"));
        System.out.println(map.containsValue(20));

        // 4. Size and empty
        System.out.println(map.size());
        System.out.println(map.isEmpty());

        // 5. Remove
        map.remove("F");
        map.remove("B", 20);

        // 6. Replace
        map.replace("A", 100);
        map.replace("C", 30, 300);

        // 7. Compute
        map.compute("A", (key, value) -> value + 5);
        map.computeIfAbsent("G", key -> 70);
        map.computeIfPresent("D", (key, value) -> value + 10);

        // 8. Merge
        map.merge("A", 10, (oldValue, newValue) ->
                oldValue + newValue);

        // 9. Get views
        Set<String> keys = map.keySet();
        Collection<Integer> values = map.values();
        Set<Map.Entry<String, Integer>> entries = map.entrySet();

        System.out.println(keys);
        System.out.println(values);
        System.out.println(entries);

        // 10. Iterate keys
        for (String key : map.keySet()) {
            System.out.println(key);
        }

        // 11. Iterate values
        for (Integer value : map.values()) {
            System.out.println(value);
        }

        // 12. Iterate entries
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " = "
                    + entry.getValue());
        }

        // 13. forEach
        map.forEach((key, value) ->
                System.out.println(key + " : " + value));

        // 14. Replace all values
        map.replaceAll((key, value) -> value + 1);

        // 15. Copy map
        Map<String, Integer> copy = new HashMap<>(map);

        // 16. Compare maps
        System.out.println(map.equals(copy));

        // 17. Hash code and String
        System.out.println(map.hashCode());
        System.out.println(map.toString());

        // 18. Get class
        System.out.println(map.getClass());

        // 19. Clear
        map.clear();
        System.out.println(map.isEmpty());
    }
}
