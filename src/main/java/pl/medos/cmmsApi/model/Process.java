//package pl.medos.cmmsApi.model;
//
//import javax.persistence.*;
//
//@Entity
//public class Process {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String name;
//
//    @OneToOne
//    @JoinColumn(name = "employee_id")
//    private Employee employee;
//    @OneToOne
//    @JoinColumn(name = "department_id")
//    private Department department;
//    @OneToOne
//    @JoinColumn(name = "machine_id")
//    private Machine machine;
//
//    public Process() {
//    }
//
//    public Process(Long id, String name, Employee employee, Department department, Machine machine) {
//        this.id = id;
//        this.name = name;
//        this.employee = employee;
//        this.department = department;
//        this.machine = machine;
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
//    public Employee getEmployee() {
//        return employee;
//    }
//
//    public void setEmployee(Employee employee) {
//        this.employee = employee;
//    }
//
//    public Department getDepartment() {
//        return department;
//    }
//
//    public void setDepartment(Department department) {
//        this.department = department;
//    }
//
//    public Machine getMachine() {
//        return machine;
//    }
//
//    public void setMachine(Machine machine) {
//        this.machine = machine;
//    }
//
//    @Override
//    public String toString() {
//        return "Process{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", employee=" + employee +
//                ", department=" + department +
//                ", machine=" + machine +
//                '}';
//    }
//}
