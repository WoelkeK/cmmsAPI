package pl.medos.cmmsApi.model;


import jakarta.persistence.Transient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
//    private User user;
    @NotNull(message = "Należy wprowadzić informację, czego dotyczy problem.")
    private Employee employee;
    private Engineer engineer;
    @NotNull(message = "Należy wprowadzić informację, czego dotyczy problem.")
    private Department department;

    @NotNull(message = "Należy wprowadzić informację, czego dotyczy problem.")
    @Valid
    private Machine machine;

    @NotEmpty(message = "Należy wprowadzić informację, czego dotyczy problem.")
    private String message;
    private Boolean directContact;
    @NotEmpty(message = "Należy uzuzpełnić opis wykonanych czynności!")
    private String solution;
//    @NotNull(message = "Należy wybrać odpowiednia datę!")
    private LocalDateTime jobStartTime;
//    @NotNull(message = "Należy wybrać odpowiednia datę!")
    private LocalDateTime jobStopTime;

    private LocalDateTime jobShedule;
    private Decision decision;
    private Integer offset;
    private DateOffset dateOffset;

    private boolean open;
    private double calcCost;
    private String status;
    private JobStatus jobStatus;
    private String photoFileName;

}
