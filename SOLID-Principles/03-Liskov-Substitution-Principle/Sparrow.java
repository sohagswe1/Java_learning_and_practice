public class Sparrow extends FlyingBird {
    @Override
    public String getName() {
        return "Sparrow";
    }

    @Override
    public void fly() {
        System.out.println("Sparrow is flying");
    }
}
