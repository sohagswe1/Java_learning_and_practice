import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListDemo {
    public static void main(String[] args) {
        CopyOnWriteArrayList<String> names = new CopyOnWriteArrayList<>();
        names.add("Sohag");
        names.add("Rahman");

        for (String name : names) {
            System.out.println(name);
            names.add("New-" + name); // Iterator uses a stable snapshot.
        }
        System.out.println(names);
    }
}
