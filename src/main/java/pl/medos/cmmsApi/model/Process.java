package pl.medos.cmmsApi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Process {

    private Long id;
    private String name;
    private List<Resource> resources;
    private Cost cost;
    private Employee employee;
    private Department department;
    private Machine machine;
}
