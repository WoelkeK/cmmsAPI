package pl.medos.cmmsApi.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import pl.medos.cmmsApi.enums.Device;
import pl.medos.cmmsApi.enums.Document;
import pl.medos.cmmsApi.enums.Status;
import pl.medos.cmmsApi.enums.Permission;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hardware {

    private Long id;
    private String name;
    private Device type;
    private Document document;
    private String systemNo;
    private String invoiceNo;
    private String inventoryNo;
    private String accountNo;
    private String serialNumber;
    private String description;
    private String macAddress;
    private String ipAddress;
    private String netBios;
    private Status status;
    private String department;
    private String employee;
    private String officeName;
    private String officeNo;
    private String bitLockKey;
    private String bitRecoveryKey;
    private Permission permission;
    private String role;
    private boolean nRead;
    private boolean nEdit;
    private boolean nFull;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate installDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate activateDate;

}
