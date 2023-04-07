package pl.medos.cmmsApi.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "JOBS")
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private LocalDateTime requestDate;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;

    @ManyToOne
    @JoinColumn(name = "engineer_id")
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
    private String message;
    private boolean directContact;
    private String solution;
    private LocalDateTime jobStartTime;
    private LocalDateTime jobStopTime;
    private double calcCost;
    private String status;
}
