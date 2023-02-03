package pl.medos.cmmsApi.model;

import pl.medos.cmmsApi.utility.Adrress;
import pl.medos.cmmsApi.utility.Contact;

import javax.persistence.*;

@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String taxNb;
    @OneToOne
    @JoinColumn(name = "adrress_id")
    private Adrress adrress;
    @ManyToOne
    @JoinColumn(name = "contact_id")
    private Contact contact;

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
}
