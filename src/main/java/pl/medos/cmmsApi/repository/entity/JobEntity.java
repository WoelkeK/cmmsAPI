package pl.medos.cmmsApi.repository.entity;

import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.model.Machine;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime requestTime;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;
    @ManyToOne
    @JoinColumn(name = "machine_id")
    private MachineEntity machine;
    private String message;
    private boolean directContact;
    private String solution;
    private LocalDateTime jobStartTime;
    private LocalDateTime jobStopTime;

    public JobEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
    }

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }

    public DepartmentEntity getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEntity department) {
        this.department = department;
    }

    public MachineEntity getMachine() {
        return machine;
    }

    public void setMachine(MachineEntity machine) {
        this.machine = machine;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getDirectContact() {
        return directContact;
    }

    public void setDirectContact(boolean directContact) {
        this.directContact = directContact;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public LocalDateTime getJobStartTime() {
        return jobStartTime;
    }

    public void setJobStartTime(LocalDateTime jobStartTime) {
        this.jobStartTime = jobStartTime;
    }

    public LocalDateTime getJobStopTime() {
        return jobStopTime;
    }

    public void setJobStopTime(LocalDateTime jobStopTime) {
        this.jobStopTime = jobStopTime;
    }

    @Override
    public String toString() {
        return "JobEntity{" +
                "id=" + id +
                ", requestTime=" + requestTime +
                ", employee=" + employee +
                ", department=" + department +
                ", machine=" + machine +
                ", message='" + message + '\'' +
                ", directContact=" + directContact +
                ", solution='" + solution + '\'' +
                ", jobStartTime=" + jobStartTime +
                ", jobStopTime=" + jobStopTime +
                '}';
    }
}
