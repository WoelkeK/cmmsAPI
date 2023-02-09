package pl.medos.cmmsApi.model;

public class Machine {

    private Long id;
    private String name;
    private String model;
    private int manufactured;
    private String serialNumber;
    private Department department;
    private String status;

    public Machine() {
    }

    public Machine(Long id, String name, String model, int manufactured, Department department, String status, String serialNumber) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.manufactured = manufactured;
        this.department = department;
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

    public int getManufactured() {
        return manufactured;
    }

    public void setManufactured(int manufactured) {
        this.manufactured = manufactured;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
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
                ", manufactured=" + manufactured +
                ", department=" + department +
                ", status='" + status + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                '}';
    }
}
