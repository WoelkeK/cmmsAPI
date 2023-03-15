package pl.medos.cmmsApi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cost {

    private Long id;
    private String unit;
    private String description;
    private double calcCost;
    private double netCost;
    private double grossCost;

}
