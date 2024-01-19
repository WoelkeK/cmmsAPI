package pl.medos.cmmsApi.repository.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "JOBS")
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @CreationTimestamp
    private LocalDateTime requestDate;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;

    @ManyToOne
    @JoinColumn(name = "engineer_id")
    @Nullable
    private EngineerEntity engineer;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;
    @ManyToOne
    @JoinColumn(name = "machine_id")
    private MachineEntity machine;
    //    @ManyToOne
//    @JoinColumn(name = "cost_id")
//    private CostEntity cost;
//    @NotEmpty(message = "Pole nie może być puste!")
    private String message;
    private boolean directContact;
//    @NotEmpty(message = "Pole nie może być puste!")
    private String solution;
    private LocalDateTime jobStartTime;
    private LocalDateTime jobStopTime;
    private double calcCost;
    private String status;
    private boolean open;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "originalImage", columnDefinition = "MEDIUMBLOB")
    private byte[] originalImage;
    @Lob()
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "resizedImage", columnDefinition = "BLOB")
    private byte[] resizedImage;
}
