package pl.medos.cmmsApi.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.medos.cmmsApi.enums.Device;
import pl.medos.cmmsApi.enums.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    //type
    private LocalDateTime pickUpDate;
    private LocalDateTime returnDate;
    private LocalDate installDate;
}
