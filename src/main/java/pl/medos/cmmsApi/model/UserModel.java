//package pl.medos.cmmsApi.model;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.util.List;
//
//@Data
//@Entity
//@Table(name="users")
//public class UserModel {
//
//    @Id
//    @GeneratedValue
//    @Column(name="user_id")
//    private Integer id;
//
//    @Column(name="user_name")
//    private String name;
//
//    @Column(name="user_passwd")
//    private String password;
//
//    @Column(name="user_email")
//    private String email;
//
//    @ElementCollection(fetch= FetchType.EAGER)
//    @CollectionTable(
//            name="roles",
//            joinColumns = @JoinColumn(name="user_id")
//    )
//    @Column(name="user_role")
//    private List<String> roles;
//
//}