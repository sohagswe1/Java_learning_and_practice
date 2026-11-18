public class Main {
    public static void main(String[] args) {
        DiscountCalculator calculator = new DiscountCalculator();

        System.out.println("Regular final price: "
            + calculator.calculateFinalPrice(1000, new RegularDiscount()));

        System.out.println("Premium final price: "
            + calculator.calculateFinalPrice(1000, new PremiumDiscount()));
    }
}
