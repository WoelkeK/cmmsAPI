package pl.medos.cmmsApi.model;

public class Resource {

    private Long id;
    private String name;
    private double quantity;

    private Invoice invoice;

    public Resource() {
    }

    public Resource(Long id, String name, double quantity, Invoice invoice) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.invoice = invoice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", invoice=" + invoice +
                '}';
    }
}
