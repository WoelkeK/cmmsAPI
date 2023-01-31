package pl.medos.cmmsApi.model;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
public class FaultReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "machine_id")
    private Machine machine;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    @JoinColumn(name = "department_id")
    private Department department;
    private String message;
    private String issueCode;
    private LocalDateTime localDateTime;

    public FaultReport() {
    }

    public FaultReport(Long id, Machine machine, User user, Department department, String message, String issueCode, LocalDateTime localDateTime) {
        this.id = id;
        this.machine = machine;
        this.user = user;
        this.department = department;
        this.message = message;
        this.issueCode = issueCode;
        this.localDateTime = localDateTime;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIssueCode() {
        return issueCode;
    }

    public void setIssueCode(String issueCode) {
        this.issueCode = issueCode;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {
        return "FaultReport{" +
                "id=" + id +
                ", machine=" + machine +
                ", user=" + user +
                ", department=" + department +
                ", message='" + message + '\'' +
                ", issueCode='" + issueCode + '\'' +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
