public class Main {
    public static void main(String[] args) {
        Printer basicPrinter = new BasicPrinter();
        basicPrinter.print("Java notes");

        MultiFunctionPrinter officeDevice = new MultiFunctionPrinter();
        officeDevice.print("SOLID notes");
        officeDevice.scan("SOLID notes");
    }
}
