public class InvoicePrinter {
    public void print(Invoice invoice) {
        System.out.println("Invoice total: " + invoice.calculateTotal());
    }
}
