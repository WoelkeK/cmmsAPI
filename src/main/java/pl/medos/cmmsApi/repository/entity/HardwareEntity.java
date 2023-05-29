package pl.medos.cmmsApi.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import pl.medos.cmmsApi.enums.Device;
import pl.medos.cmmsApi.enums.Status;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.model.Software;

import java.time.LocalDate;
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
    private String invoiceNo;
    @Enumerated(EnumType.STRING)
    private Device type;
    private String serialNumber;
    private String description;
    private String macAddress;
    private String ipAddress;
    private String netBios;
    @Enumerated(EnumType.STRING)
    private Status status;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate pickUpDate;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate returnDate;
    private LocalDate installDate;
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