package pl.medos.cmmsApi.repository.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestTime;

//    @UpdateTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "modify_date")
//    private Date modifyDate;

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
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String jobStartTime;
    private String jobStopTime;

    public JobEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isDirectContact() {
        return directContact;
    }

    public String getJobStartTime() {
        return jobStartTime;
    }

    public void setJobStartTime(String jobStartTime) {
        this.jobStartTime = jobStartTime;
    }

    public String getJobStopTime() {
        return jobStopTime;
    }

    public void setJobStopTime(String jobStopTime) {
        this.jobStopTime = jobStopTime;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
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
                ", jobStartTime='" + jobStartTime + '\'' +
                ", jobStopTime='" + jobStopTime + '\'' +
                '}';
    }
}
