package pl.medos.cmmsApi.model;

public class Supplier {

    private Long id;
    private String name;
    private String taxNb;
    private Adrress adrress;
    private Contact contact;
    public Supplier() {
    }

    public Supplier(Long id, String name, String taxNb, Adrress adrress, Contact contact) {
        this.id = id;
        this.name = name;
        this.taxNb = taxNb;
        this.adrress = adrress;
        this.contact = contact;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Adrress getAdrress() {
        return adrress;
    }

    public void setAdrress(Adrress adrress) {
        this.adrress = adrress;
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
                ", adrress=" + adrress +
                ", contact=" + contact +
                '}';
    }
}
