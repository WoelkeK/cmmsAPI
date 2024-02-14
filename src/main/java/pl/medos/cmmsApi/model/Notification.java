package pl.medos.cmmsApi.model;

import jakarta.validation.constraints.NotEmpty;
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
    private LocalDateTime visitDate;
    private Type type;
    private NotificationStatus status;
    @NotEmpty(message = "Pole nie może być puste!")
    private String supplier;
    private String item;
    private String itemDetails;
    @NotEmpty(message = "Pole nie może być puste!")
    private String carPlates;
    private String driverName;
    private String driverPhone;
    @NotEmpty(message = "Pole nie może być puste!")
    private String employee;
    @NotEmpty(message = "Pole nie może być puste!")
    private String employeePhone;
    private String description;
    private String comment;
    private int piece;
    private String trailerPlates;

//    private byte[] originalImage;
//    private byte[] resizedImage;
}
