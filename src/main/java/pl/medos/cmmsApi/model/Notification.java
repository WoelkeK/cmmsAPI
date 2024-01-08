package pl.medos.cmmsApi.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    private String driverName;
    @NotEmpty(message = "Pole nie może być puste!")
    private String supplier;
    private String driverPhone;
    @NotEmpty(message = "Pole nie może być puste!")
    private String employee;
    @NotEmpty(message = "Pole nie może być puste!")
    private String employeePhone;
    @NotEmpty(message = "Pole nie może być puste!")
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

//    private byte[] originalImage;
//    private byte[] resizedImage;
}
