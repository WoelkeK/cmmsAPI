package pl.medos.cmmsApi.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import pl.medos.cmmsApi.model.Status;
import pl.medos.cmmsApi.model.Type;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Data
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String driverName;
    @NotEmpty(message = "Pole nie może być puste!")
    private String supplier;
    private String driverPhone;
    @NotEmpty(message = "Pole nie może być puste!")
    private String employeePhone;
    @NotEmpty(message = "Pole nie może być puste!")
    private String employee;
    @NotEmpty(message = "Pole nie może być puste!")
    private String carPlates;
    private String trailerPlates;
    private String item;
    private String itemDetails;
    private int piece;
    private String description;
    private String comment;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Type type;
    private LocalDateTime visitDate;

//    @Lob
//    @Basic(fetch = FetchType.LAZY)
//    @Column(name = "originalImage", columnDefinition = "MEDIUMBLOB")
//    private byte[] originalImage;
//    @Lob()
//    @Basic(fetch = FetchType.LAZY)
//    @Column(name = "resizedImage", columnDefinition = "BLOB")
//    private byte[] resizedImage;

}