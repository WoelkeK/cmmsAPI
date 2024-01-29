package pl.medos.cmmsApi.model;


import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import pl.medos.cmmsApi.enums.DateOffset;
import pl.medos.cmmsApi.enums.Decision;
import pl.medos.cmmsApi.enums.JobStatus;

import java.time.LocalDateTime;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JsonJob {

    private String requestDate;
//    private String user;
    private String employee;
    private String engineer;
    private String department;
    private String machine;
    private String message;
//    private String directContact;
    private String solution;
    private String jobStartTime;
    private String jobStopTime;

    private String jobShedule;
    private Decision decision;
    private String dateOffset;
    private String status;
    private JobStatus jobStatus;
    private String open;
    private String offset;




//    private byte[] originalImage;
//    private byte[] resizedImage;

}
