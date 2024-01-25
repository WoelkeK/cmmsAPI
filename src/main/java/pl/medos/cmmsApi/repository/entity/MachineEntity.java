package pl.medos.cmmsApi.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import pl.medos.cmmsApi.enums.Operate;

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
//    @Enumerated(EnumType.STRING)
    private String status;

//    @ManyToOne
//    @JoinColumn(name = "maintenance_id")
//    private MaintenenceEntity maintenence;

    @CreationTimestamp
    private LocalDateTime installDate;


    @OneToMany(mappedBy = "machine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobEntity> jobEntities;
}
