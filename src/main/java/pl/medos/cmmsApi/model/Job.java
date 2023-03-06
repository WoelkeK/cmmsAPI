package pl.medos.cmmsApi.model;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDateTime;
import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    private Long id;
    private LocalDateTime requestDate;
    private User user;
    private Employee employee;
    private Department department;
    private Machine machine;
    @NotEmpty
    private String message;
    private Boolean directContact;
    private String solution;
    private LocalDateTime jobStartTime;
    private LocalDateTime jobStopTime;

}
