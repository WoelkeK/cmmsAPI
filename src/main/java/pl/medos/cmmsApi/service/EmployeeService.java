package pl.medos.cmmsApi.service;

import pl.medos.cmmsApi.exception.EmployeeNotFoundException;
import pl.medos.cmmsApi.model.Employee;
import java.util.List;

public interface EmployeeService {

    List<Employee> finadAllEmployees();

    Employee createEmployee(Employee employee);

    Employee findEmployeeById(Long id) throws EmployeeNotFoundException;

    Employee updateEmployee(Employee employee);

    void deleteEmployee(Long id);
}
