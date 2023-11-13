package pl.medos.cmmsApi.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import pl.medos.cmmsApi.enums.Status;
import pl.medos.cmmsApi.model.Type;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String driverName;
    private String supplier;
    private String driverPhone;
    private String employee;
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

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "originalImage", columnDefinition = "MEDIUMBLOB")
    private byte[] originalImage;
    @Lob()
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "resizedImage", columnDefinition = "BLOB")
    private byte[] resizedImage;

}