package pl.medos.cmmsApi.repository.entity;

import pl.medos.cmmsApi.model.Department;

import javax.persistence.*;

@Entity
public class MachineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;
    private String name;
    private String model;
    private int manufactured;
    private String serialNumber;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;
    private String status;


    public MachineEntity() {
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

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public DepartmentEntity getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEntity department) {
        this.department = department;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MachineEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", manufactured=" + manufactured +
                ", serialNumber='" + serialNumber + '\'' +
                ", department=" + department +
                ", status='" + status + '\'' +
                '}';
    }
}
