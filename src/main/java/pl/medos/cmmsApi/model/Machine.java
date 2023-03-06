package pl.medos.cmmsApi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Machine {

    private Long id;
    private String name;
    private String model;
    private int manufactured;
    private String serialNumber;
    private Department department;
    private String status;
}
