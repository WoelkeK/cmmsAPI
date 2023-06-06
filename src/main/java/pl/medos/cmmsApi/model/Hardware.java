package pl.medos.cmmsApi.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import pl.medos.cmmsApi.enums.Device;
import pl.medos.cmmsApi.enums.Status;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hardware {

    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Device type;
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
    private Department department;
    private Employee employee;

    private String officeName;
    private String officeNo;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate activateDate;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate returnDate;
    private LocalDate installDate;
}
