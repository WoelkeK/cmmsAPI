package pl.medos.cmmsApi.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.model.Software;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "HARDWARES")
public class HardwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;
    private String name;
    private String systemNo;
    private String inventoryNo;
    private String type;
    private String serialNumber;
    private String description;
    private String macAddress;
    private String ipAddress;
    private String status;
    private LocalDateTime pickUpDate;
    private LocalDateTime returnDate;
    private LocalDateTime installDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "software_id")
//    private Software software;

}
