package pl.medos.cmmsApi.model;

import javax.persistence.*;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String position;
    private String occupation;
    private String userName;
    private String phone;
    private String email;
    private String department;
    private String comments;
    private String jobs;
    private String raports;
    private String message;
    private String pushNothing;
    private String pushEmail;
    private String pushEverythink;
    private String about;


//    @OneToOne
//    @JoinColumn(name = "contact_id")
//    private Contact contact;

    public Employee() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getJobs() {
        return jobs;
    }

    public void setJobs(String jobs) {
        this.jobs = jobs;
    }

    public String getRaports() {
        return raports;
    }

    public void setRaports(String raports) {
        this.raports = raports;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPushNothing() {
        return pushNothing;
    }

    public void setPushNothing(String pushNothing) {
        this.pushNothing = pushNothing;
    }

    public String getPushEmail() {
        return pushEmail;
    }

    public void setPushEmail(String pushEmail) {
        this.pushEmail = pushEmail;
    }

    public String getPushEverythink() {
        return pushEverythink;
    }

    public void setPushEverythink(String pushEverythink) {
        this.pushEverythink = pushEverythink;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", position='" + position + '\'' +
                ", occupation='" + occupation + '\'' +
                ", userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", department='" + department + '\'' +
                ", comments='" + comments + '\'' +
                ", jobs='" + jobs + '\'' +
                ", raports='" + raports + '\'' +
                ", message='" + message + '\'' +
                ", pushNothing='" + pushNothing + '\'' +
                ", pushEmail='" + pushEmail + '\'' +
                ", pushEverythink='" + pushEverythink + '\'' +
                ", about='" + about + '\'' +
                '}';
    }
}
