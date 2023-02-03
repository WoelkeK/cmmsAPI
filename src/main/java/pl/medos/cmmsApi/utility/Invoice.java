package pl.medos.cmmsApi.utility;

import pl.medos.cmmsApi.model.Supplier;

import javax.persistence.*;

@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    @OneToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    public Invoice() {
    }

    public Invoice(Long id, String number, Supplier supplier) {
        this.id = id;
        this.number = number;
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
                ", supplier=" + supplier +
                '}';
    }
}
