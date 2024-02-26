package pl.medos.cmmsApi.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.model.Engineer;

import java.util.ArrayList;
import java.util.List;

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
    private Long id;
    private String name;
    private String location;

    @OneToMany(mappedBy = "department", cascade = CascadeType.REMOVE)
    private
    List<MachineEntity> machines = new ArrayList<>();

    @OneToMany(mappedBy = "department", cascade = CascadeType.REMOVE)
    private
    List<JobEntity> jobs = new ArrayList<>();

    @OneToMany(mappedBy = "department", cascade = CascadeType.REMOVE)
    private List<EngineerEntity> engineers;


}
