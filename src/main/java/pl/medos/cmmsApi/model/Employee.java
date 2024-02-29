package pl.medos.cmmsApi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    private Long id;
    private String name;
    private String phone;
    private String email;
    private String position;
    private Department department;
    private Boolean profile;

}
