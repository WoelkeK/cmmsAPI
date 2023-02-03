//package pl.medos.cmmsApi.model;
//
//import javax.persistence.*;
//
//@Entity
//public class Supplier {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String name;
//    private String taxNb;
//    @OneToOne
//    @JoinColumn(name = "adrress_id")
//    private Adrress adrress;
//    @ManyToOne
//    @JoinColumn(name = "contact_id")
//    private Contact contact;
//
//    public Supplier() {
//    }
//
//    public Supplier(Long id, String name, String taxNb, Adrress adrress, Contact contact) {
//        this.id = id;
//        this.name = name;
//        this.taxNb = taxNb;
//        this.adrress = adrress;
//        this.contact = contact;
//    }
//
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
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getTaxNb() {
//        return taxNb;
//    }
//
//    public void setTaxNb(String taxNb) {
//        this.taxNb = taxNb;
//    }
//
//    @Override
//    public String toString() {
//        return "Supplier{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", taxNb='" + taxNb + '\'' +
//                ", adrress=" + adrress +
//                ", contact=" + contact +
//                '}';
//    }
//}
