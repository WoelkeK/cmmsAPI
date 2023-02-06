package pl.medos.cmmsApi.model;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

public class Job {

    private Long id;
    private LocalDateTime requestTime;
    private User user;
    private Department department;
    private Machine machine;
    private String message;
    private Boolean directContact;
    private String solution;
    private LocalDateTime jobStartTime;
    private LocalDateTime jobStopTime;

    public Job() {
    }

    public Job(Long id, LocalDateTime requestTime, User user, Department department, Machine machine, String message, Boolean directContact, String solution, LocalDateTime jobStartTime, LocalDateTime jobStopTime) {
        this.id = id;
        this.requestTime = requestTime;
        this.user = user;
        this.department = department;
        this.machine = machine;
        this.message = message;
        this.directContact = directContact;
        this.solution = solution;
        this.jobStartTime = jobStartTime;
        this.jobStopTime = jobStopTime;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getDirectContact() {
        return directContact;
    }

    public void setDirectContact(Boolean directContact) {
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
        return "Jobs{" +
                "id=" + id +
                ", requestTime=" + requestTime +
                ", user=" + user +
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
