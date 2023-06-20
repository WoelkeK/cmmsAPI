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

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JsonHardware {

    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Device type;
    private String document;
    private String systemNo;
    private String invoiceNo;
    private String inventoryNo;
    private String accountNo;
    private String serialNumber;
    private String description;
    private String macAddress;
    private String ipAddress;
    private String netBios;

    @Enumerated(EnumType.STRING)
    private Status status;
    private String department;
    private String employee;
    private String officeName;
    private String officeNo;
    private String activateDate;

}
