package pl.medos.cmmsApi.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "EMPLOYEES")
public class EmployeeEntity {
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
    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;

}
