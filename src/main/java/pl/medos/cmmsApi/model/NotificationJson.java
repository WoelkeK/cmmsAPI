package pl.medos.cmmsApi.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class NotificationJson {

    private Long id;
    private String visitDate;
    private Type type;
    private NotificationStatus status;
    private String supplier;
    private String item;
    private String itemDetails;
    private String carPlates;
    private String driverName;
    private String driverPhone;
    private String employee;
    private String employeePhone;
    private String description;
    private String comment;
    private String piece;
    private String trailerPlates;

//    private byte[] originalImage;
//    private byte[] resizedImage;
}
