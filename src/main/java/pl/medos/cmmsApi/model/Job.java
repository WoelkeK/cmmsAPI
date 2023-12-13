package pl.medos.cmmsApi.model;


import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    private Long id;
    private LocalDateTime requestDate;
    private User user;
    private Employee employee;
    private String engineer;
    private Department department;
    private Machine machine;
    //    private Cost cost;
    @NotEmpty(message = "Należy wprowadzić informację, czego dotyczy problem.")
    private String message;
    private Boolean directContact;
    @NotEmpty(message = "Należy uzuzpełnić opis wykonanych czynności!")
    private String solution;
    private LocalDateTime jobStartTime;
    private LocalDateTime jobStopTime;
    private double calcCost;
    private String status;

    private byte[] originalImage;
    private byte[] resizedImage;

}
