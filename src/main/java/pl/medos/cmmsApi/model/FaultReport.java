package pl.medos.cmmsApi.model;

import java.time.LocalDateTime;

public class FaultReport {

    private Long id;
    private Machine machine;
    private User user;
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
