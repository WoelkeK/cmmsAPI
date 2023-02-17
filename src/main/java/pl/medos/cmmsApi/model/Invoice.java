package pl.medos.cmmsApi.model;

public class Invoice {

    private Long id;
    private String number;

    private double price;
    private Supplier supplier;

    public Invoice() {
    }

    public Invoice(Long id, String number, double price, Supplier supplier) {
        this.id = id;
        this.number = number;
        this.price = price;
        this.supplier = supplier;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", price=" + price +
                ", supplier=" + supplier +
                '}';
    }
}
