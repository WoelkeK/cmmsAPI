package pl.medos.cmmsApi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Software {

    private Long id;
    private String name;
    private String number;
    private String version;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate activateDate;


}
