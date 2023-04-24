package pl.medos.cmmsApi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.medos.cmmsApi.repository.entity.MaintenenceEntity;

import java.time.LocalDateTime;
import java.util.List;

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
    private LocalDateTime installDate;
//    private Maintenance maintenance;
}
