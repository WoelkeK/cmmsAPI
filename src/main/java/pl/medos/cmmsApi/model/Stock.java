package pl.medos.cmmsApi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

    private Long id;
    private String name;
    private int stockRegistry;
    private Part part;
    private Cost cost;

}
