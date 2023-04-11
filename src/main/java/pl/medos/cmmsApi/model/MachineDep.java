package pl.medos.cmmsApi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MachineDep {

    private Long id;
    private String name;
    private String model;
    private String manufactured;
    private String serialNumber;
    private String department;
    private String status;
    private String installDate;


}
