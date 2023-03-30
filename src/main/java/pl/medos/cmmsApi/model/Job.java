package pl.medos.cmmsApi.model;


import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;


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
    private Department department;
    private Machine machine;
    //    private Cost cost;
    @NotEmpty
    private String message;
    private Boolean directContact;
    private String solution;
    private LocalDateTime jobStartTime;
    private LocalDateTime jobStopTime;
    private double calcCost;
    private String status;

}
