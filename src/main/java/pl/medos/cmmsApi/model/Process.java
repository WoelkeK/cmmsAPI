package pl.medos.cmmsApi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Process {

    private Long id;
    private String name;
    private Employee employee;
    private Department department;
    private Machine machine;
}
