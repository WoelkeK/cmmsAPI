package pl.medos.cmmsApi.model;


import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.enums.DateOffset;
import pl.medos.cmmsApi.enums.Decision;
import pl.medos.cmmsApi.enums.JobStatus;


import java.time.LocalDate;
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
    private Engineer engineer;
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

    private LocalDateTime jobShedule;
    private Decision decision;
    private Integer offset;
    private DateOffset dateOffset;

    private boolean open;

    private double calcCost;
    private String status;

    private JobStatus jobStatus;

    private byte[] originalImage;
    private byte[] resizedImage;

}
