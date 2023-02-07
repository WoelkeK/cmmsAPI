package pl.medos.cmmsApi.model;

public class Adrress {

    private Long id;
    private String city;
    private String postCode;
    private String street;
    private int number;

    public Adrress() {
    }

    public Adrress(Long id, String city, String postCode, String street, int number) {
        this.id = id;
        this.city = city;
        this.postCode = postCode;
        this.street = street;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Adrress{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", postCode='" + postCode + '\'' +
                ", street='" + street + '\'' +
                ", number=" + number +
                '}';
    }
}
