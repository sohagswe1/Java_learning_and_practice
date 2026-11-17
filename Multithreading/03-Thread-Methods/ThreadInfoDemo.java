public class ThreadInfoDemo {
    public static void main(String[] args) {
        Thread current = Thread.currentThread();
        current.setName("main-custom");
        System.out.println("Name: " + current.getName());
        System.out.println("ID: " + current.getId());
        System.out.println("State: " + current.getState());
    }
}
