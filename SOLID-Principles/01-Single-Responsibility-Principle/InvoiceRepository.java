public class InvoiceRepository {
    public void save(Invoice invoice) {
        // Demo only: a real repository would persist data to a database.
        System.out.println("Invoice saved. Total = " + invoice.calculateTotal());
    }
}
