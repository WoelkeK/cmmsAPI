//package pl.medos.cmmsApi.repository.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.List;
//
//@Setter
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name="roles")
//public class RoleEntity
//{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable=false, unique=true)
//    private String name;
//
//    @ManyToMany(mappedBy= "roleEntities")
//    private List<UserEntity> userEntities;
//}
