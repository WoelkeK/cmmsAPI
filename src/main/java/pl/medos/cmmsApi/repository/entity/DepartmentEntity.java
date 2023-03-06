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
@Table(name = "DEPARTMENTS")
public class DepartmentEntity {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(unique = true)
    private Long id;
    private String name;
    private String location;

}
