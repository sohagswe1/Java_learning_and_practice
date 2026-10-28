
import java.util.Arrays;
import java.util.StringJoiner;

public class StringAllMethods {

    public static void main(String[] args) {

        String str = "Hello Java World";
        String str2 = "hello java world";
        String empty = "";
        String text = "  Java Programming  ";

        // 1. Length
        System.out.println(str.length());

        // 2. Character access
        System.out.println(str.charAt(0));

        // 3. Substring
        System.out.println(str.substring(6));
        System.out.println(str.substring(6, 10));

        // 4. Contains
        System.out.println(str.contains("Java"));

        // 5. Equals
        System.out.println(str.equals(str2));
        System.out.println(str.equalsIgnoreCase(str2));

        // 6. Compare strings
        System.out.println(str.compareTo(str2));
        System.out.println(str.compareToIgnoreCase(str2));

        // 7. StartsWith and EndsWith
        System.out.println(str.startsWith("Hello"));
        System.out.println(str.endsWith("World"));

        // 8. Index methods
        System.out.println(str.indexOf('a'));
        System.out.println(str.indexOf("Java"));
        System.out.println(str.indexOf('a', 7));
        System.out.println(str.lastIndexOf('a'));

        // 9. Empty and blank checks (Java 11+)
        System.out.println(empty.isEmpty());
        System.out.println("   ".isBlank());

        // 10. Uppercase and lowercase
        System.out.println(str.toUpperCase());
        System.out.println(str.toLowerCase());

        // 11. Remove surrounding whitespace
        System.out.println(text.trim());
        System.out.println(text.strip());          // Java 11+
        System.out.println(text.stripLeading());   // Java 11+
        System.out.println(text.stripTrailing());  // Java 11+

        // 12. Replace
        System.out.println(str.replace('a', 'A'));
        System.out.println(str.replace("Java", "Python"));
        System.out.println(str.replaceFirst("a", "A"));
        System.out.println(str.replaceAll("a", "A"));

        // 13. Split
        String csv = "Java,Python,C++";
        String[] languages = csv.split(",");
        System.out.println(Arrays.toString(languages));

        // 14. Regex matching
        System.out.println("12345".matches("\\d+"));
        System.out.println("Java123".matches("[A-Za-z0-9]+"));

        // 15. Concatenation
        System.out.println("Hello".concat(" Java"));
        System.out.println("Hello" + " Java");

        // 16. Static join
        System.out.println(String.join("-", "2026", "10", "28"));

        // 17. Repeat (Java 11+)
        System.out.println("Java ".repeat(3));

        // 18. Convert to character array
        char[] chars = str.toCharArray();
        System.out.println(Arrays.toString(chars));

        // 19. Convert to bytes
        byte[] bytes = str.getBytes();
        System.out.println(Arrays.toString(bytes));

        // 20. Convert other types to String
        System.out.println(String.valueOf(100));
        System.out.println(String.valueOf(10.5));
        System.out.println(String.valueOf(true));
        System.out.println(String.valueOf('A'));

        // 21. Format
        System.out.println(String.format(
                "Name: %s, Age: %d", "Sohag", 25));

        // 22. Split into lines (Java 11+)
        String multiline = "Java\nPython\nC++";
        multiline.lines().forEach(System.out::println);

        // 23. Check region
        System.out.println(str.regionMatches(
                6, "Java Programming", 0, 4));

        // 24. Check content
        System.out.println(str.contains("World"));
        System.out.println(str.contentEquals("Hello Java World"));

        // 25. Character sequences
        System.out.println(str.subSequence(0, 5));

        // 26. String to integer / double
        System.out.println(Integer.parseInt("123"));
        System.out.println(Double.parseDouble("12.50"));

        // 27. StringBuilder conversion
        StringBuilder sb = new StringBuilder(str);
        System.out.println(sb.reverse());

        // 28. Intern
        String s1 = new String("Java");
        String s2 = s1.intern();
        System.out.println(s2);

        // 29. Hash code
        System.out.println(str.hashCode());

        // 30. StringJoiner
        StringJoiner joiner = new StringJoiner(", ");
        joiner.add("Java");
        joiner.add("Spring Boot");
        joiner.add("Microservices");
        System.out.println(joiner);
    }
}
