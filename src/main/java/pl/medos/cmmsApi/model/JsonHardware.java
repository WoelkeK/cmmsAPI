package pl.medos.cmmsApi.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.medos.cmmsApi.enums.Device;
import pl.medos.cmmsApi.enums.Status;
import pl.medos.cmmsApi.enums.Permission;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JsonHardware {

    private String id;
    private String name;
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
    private String bitLockKey;
    private String bitRecoveryKey;
    private Status status;
    private String department;
    private String employee;
    private String officeName;
    private String officeNo;
    private String activateDate;
    private String installDate;
    private Permission permission;

    private String nRead;
    private String nEdit;
    private String nDelete;
    private String eRead;
    private String eEdit;
    private String eDelete;
    private String pRead;
    private String pEdit;
    private String pDelete;
    private String dRead;
    private String dEdit;
    private String dDelete;
    private String mRead;
    private String mEdit;
    private String mDelete;
    private String jRead;
    private String jEdit;
    private String jDelete;

}
