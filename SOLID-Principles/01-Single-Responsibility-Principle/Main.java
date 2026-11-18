import java.util.List;

public class Main {
    public static void main(String[] args) {
        Invoice invoice = new Invoice(List.of(100.0, 250.0, 50.0));

        new InvoicePrinter().print(invoice);
        new InvoiceRepository().save(invoice);
    }
}
