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
    private String firstName;
    private String lastName;
    private String position;
    private String occupation;
    private String phone;
    private String email;
    private Department department;

}
