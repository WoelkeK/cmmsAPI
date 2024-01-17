package pl.medos.cmmsApi.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import pl.medos.cmmsApi.enums.Device;
import pl.medos.cmmsApi.enums.Document;
import pl.medos.cmmsApi.enums.Status;
import pl.medos.cmmsApi.enums.Permission;

import java.time.LocalDate;

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
    @Enumerated(EnumType.STRING)
    private Document document;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate installDate;
    private String employee;
    private String department;
    private String officeName;
    private String officeNo;
    private String bitLockKey;
    private String bitRecoveryKey;

    private boolean nRead;
    private boolean nEdit;
    private boolean nDelete;
    private boolean eRead;
    private boolean eEdit;
    private boolean eDelete;
    private boolean pRead;
    private boolean pEdit;
    private boolean pDelete;
    private boolean dRead;
    private boolean dEdit;
    private boolean dDelete;
    private boolean mRead;
    private boolean mEdit;
    private boolean mDelete;
    private boolean jRead;
    private boolean jEdit;
    private boolean jDelete;

    @Enumerated(EnumType.STRING)
    private Permission permission;
    private String role;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate activateDate;


}
