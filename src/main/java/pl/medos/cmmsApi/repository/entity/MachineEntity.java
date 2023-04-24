package pl.medos.cmmsApi.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "MACHINES")
public class MachineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;
    private String name;
    private String model;
    private int manufactured;
    private String serialNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;
    private String status;

//    @ManyToOne
//    @JoinColumn(name = "maintenance_id")
//    private MaintenenceEntity maintenence;

    @CreationTimestamp
    private LocalDateTime installDate;

}
