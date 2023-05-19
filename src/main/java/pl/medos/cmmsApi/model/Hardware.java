package pl.medos.cmmsApi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hardware {

    private Long id;
    private String name;
    private String type;
    private String systemNo;
    private String inventoryNo;
    private String  accountNo;
    private String  serviceTag;
    private String serialNumber;
    private String description;
    private String macAddress;
    private String ipAddress;
    private String netBios;
    private String status;
    private Department department;
    private Employee employee;
    private Software software;
    private LocalDateTime pickUpDate;
    private LocalDateTime returnDate;
    private LocalDateTime installDate;
}
