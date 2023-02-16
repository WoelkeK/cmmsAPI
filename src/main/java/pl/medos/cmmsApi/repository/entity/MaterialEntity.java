package pl.medos.cmmsApi.repository.entity;

import jakarta.persistence.*;
import pl.medos.cmmsApi.model.Invoice;
import pl.medos.cmmsApi.model.Supplier;

@Entity
@Table(name = "MATERIALS")
public class MaterialEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String index;
    private double cost;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private SupplierEntity supplier;
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private InvoiceEntity invoice;

    public MaterialEntity() {
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

    public SupplierEntity getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierEntity supplier) {
        this.supplier = supplier;
    }

    public InvoiceEntity getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceEntity invoice) {
        this.invoice = invoice;
    }

    @Override
    public String toString() {
        return "MaterialEntity{" +
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
