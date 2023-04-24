package pl.medos.cmmsApi.service;

import pl.medos.cmmsApi.exception.EmployeeNotFoundException;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.model.Engineer;

import java.util.List;

public interface EngineerService {

    List<Engineer> finadAllEmployees();

    Engineer createEmployee(Engineer employee);

    Engineer findEmployeeById(Long id) throws EmployeeNotFoundException;

    Engineer updateEmployee(Engineer employee, Long id) throws EmployeeNotFoundException;

    void deleteEmployee(Long id);

   List<Engineer> findEmployeeByName(String employeeName);
}
