package pl.medos.cmmsApi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.medos.cmmsApi.model.Employee;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesImportDto {
    private List<Employee> employees;

    public void addEmployee(Employee employee){
        this.employees.add(employee);
    }

}
