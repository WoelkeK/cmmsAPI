package pl.medos.cmmsApi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Machine {

    private Long id;
    private String name;
    private String model;
    private int manufactureYear;
    private String location;
    private String status;
    private String serialNumber;

    public Machine() {
    }

    public Machine(Long id, String name, String model, int manufactureYear, String location, String status, String serialNumber) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.manufactureYear = manufactureYear;
        this.location = location;
        this.status = status;
        this.serialNumber = serialNumber;
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(int manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public String toString() {
        return "Machine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", manufactureYear=" + manufactureYear +
                ", location='" + location + '\'' +
                ", status='" + status + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                '}';
    }
}
