package pl.medos.cmmsApi.repository.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime requestTime;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentEntity departmentEntity;
    @ManyToOne
    @JoinColumn(name = "machine_id")
    private MachineEntity machineEntity;
    private String message;
    private Boolean directContact;
    private String solution;
    private LocalDateTime jobStartTime;
    private LocalDateTime jobStopTime;

    public JobEntity() {
    }

    public MachineEntity getMachine() {
        return machineEntity;
    }

    public void setMachine(MachineEntity machineEntity) {
        this.machineEntity = machineEntity;
    }

    public DepartmentEntity getDepartment() {
        return departmentEntity;
    }

    public void setDepartment(DepartmentEntity departmentEntity) {
        this.departmentEntity = departmentEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
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
        return "JobEntity{" +
                "id=" + id +
                ", requestTime=" + requestTime +
                ", userEntity=" + userEntity +
                ", departmentEntity=" + departmentEntity +
                ", machineEntity=" + machineEntity +
                ", message='" + message + '\'' +
                ", directContact=" + directContact +
                ", solution='" + solution + '\'' +
                ", jobStartTime=" + jobStartTime +
                ", jobStopTime=" + jobStopTime +
                '}';
    }
}
