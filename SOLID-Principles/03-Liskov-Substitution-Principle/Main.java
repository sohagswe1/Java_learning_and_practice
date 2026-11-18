import java.util.List;

public class Main {
    private static void makeBirdFly(FlyingBird bird) {
        bird.fly();
    }

    public static void main(String[] args) {
        List<Bird> birds = List.of(new Sparrow(), new Penguin());

        for (Bird bird : birds) {
            System.out.println("Bird: " + bird.getName());
        }

        makeBirdFly(new Sparrow());
        new Penguin().swim();
    }
}
