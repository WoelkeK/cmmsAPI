package pl.medos.cmmsApi.utility;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private int phoneNb;

    public Contact() {
    }

    public Contact(Long id, String email, int phoneNb) {
        this.id = id;
        this.email = email;
        this.phoneNb = phoneNb;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNb() {
        return phoneNb;
    }

    public void setPhoneNb(int phoneNb) {
        this.phoneNb = phoneNb;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", phoneNb=" + phoneNb +
                '}';
    }
}
