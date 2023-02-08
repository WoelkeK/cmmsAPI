package pl.medos.cmmsApi.model;


import java.util.Date;

public class Job {

    private Long id;
    private Date requestTime;
    private User user;
    private Employee employee;
    private Department department;
    private Machine machine;
    private String message;
    private Boolean directContact;
    private String solution;
    private String jobStartTime;
    private String jobStopTime;

    public Job() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
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

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", requestTime=" + requestTime +
                ", user=" + user +
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
