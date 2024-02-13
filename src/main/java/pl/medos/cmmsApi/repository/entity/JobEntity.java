package pl.medos.cmmsApi.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import pl.medos.cmmsApi.enums.DateOffset;
import pl.medos.cmmsApi.enums.Decision;
import pl.medos.cmmsApi.enums.JobStatus;

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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id")
    private MachineEntity machine;




    private String message;
    private boolean directContact;
//    @NotEmpty(message = "Pole nie może być puste!")
    private String solution;
    private LocalDateTime jobStartTime;
    private LocalDateTime jobStopTime;
    private LocalDateTime jobShedule;
    @Enumerated(EnumType.STRING)
    private Decision decision;
    @Nullable
    private Integer offset;
    @Enumerated(EnumType.STRING)
    private DateOffset dateOffset;

    private double calcCost;
    private String status;

    private boolean open;

    @Enumerated(EnumType.STRING)
    private JobStatus jobStatus;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "originalImage", columnDefinition = "MEDIUMBLOB")
    private byte[] originalImage;
    @Lob()
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "resizedImage", columnDefinition = "MEDIUMBLOB")
    private byte[] resizedImage;
}
