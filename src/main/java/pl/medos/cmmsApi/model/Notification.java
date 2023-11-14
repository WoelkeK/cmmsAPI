package pl.medos.cmmsApi.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Notification {

    private Long id;
    private String driverName;
    private String supplier;
    private String driverPhone;
    private String employee;
    private String employeePhone;
    private String carPlates;
    private String trailerPlates;
    private String item;
    private String itemDetails;
    private int piece;
    private String description;
    private String comment;
    private Type type;
    private Status status;
    private LocalDateTime visitDate;

    private byte[] originalImage;
    private byte[] resizedImage;
}
