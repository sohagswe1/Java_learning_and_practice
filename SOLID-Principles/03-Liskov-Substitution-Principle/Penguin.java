public class Penguin extends Bird {
    @Override
    public String getName() {
        return "Penguin";
    }

    public void swim() {
        System.out.println("Penguin is swimming");
    }
}
