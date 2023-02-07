package pl.medos.cmmsApi.model;

public class Material {

    private Long id;
    private String name;
    private String index;
    private double cost;
    private int quantity;
    private Supplier supplier;
    private Invoice invoice;

    public Material() {
    }

    public Material(Long id, String name, String index, double cost, int quantity, Supplier supplier, Invoice invoice) {
        this.id = id;
        this.name = name;
        this.index = index;
        this.cost = cost;
        this.quantity = quantity;
        this.supplier = supplier;
        this.invoice = invoice;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
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

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
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
        return "Material{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", index='" + index + '\'' +
                ", cost=" + cost +
                ", quantity=" + quantity +
                ", supplier=" + supplier +
                ", invoice=" + invoice +
                '}';
    }
}
