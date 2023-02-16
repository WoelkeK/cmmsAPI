package pl.medos.cmmsApi.repository.entity;

import jakarta.persistence.*;
import pl.medos.cmmsApi.model.Adrress;
import pl.medos.cmmsApi.model.Contact;

@Entity
@Table(name = "SUPPLIERS")
public class SupplierEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String taxNb;
//    private Adrress adrress;
//    private Contact contact;

    public SupplierEntity() {
    }

//    public Contact getContact() {
//        return contact;
//    }
//
//    public void setContact(Contact contact) {
//        this.contact = contact;
//    }
//
//    public Adrress getAdrress() {
//        return adrress;
//    }
//
//    public void setAdrress(Adrress adrress) {
//        this.adrress = adrress;
//    }

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

    public String getTaxNb() {
        return taxNb;
    }

    public void setTaxNb(String taxNb) {
        this.taxNb = taxNb;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", taxNb='" + taxNb + '\'' +
//                ", adrress=" + adrress +
//                ", contact=" + contact +
                '}';
    }
}
