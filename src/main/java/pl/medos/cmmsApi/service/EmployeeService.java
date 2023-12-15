package pl.medos.cmmsApi.service;

import org.springframework.data.domain.Page;
import pl.medos.cmmsApi.exception.EmployeeNotFoundException;
import pl.medos.cmmsApi.model.Employee;
import java.util.List;

public interface EmployeeService {

    List<Employee> finadAllEmployees();

    Employee createEmployee(Employee employee);

    Employee findEmployeeById(Long id) throws EmployeeNotFoundException;

    Employee updateEmployee(Employee employee, Long id) throws EmployeeNotFoundException;

    void deleteEmployee(Long id);

    Page<Employee> findEmployeeByName(int pageNo, int pagesize, String employeeName);

    List<Employee> findEmployeeByRawName(String employeeName);

    Page<Employee> findPageinated(int pageNo, int pagesize, String sortField, String sortDir);

    Page<Employee> findPageinatedQuery(int pageNo, int pageSize, String sortField, String sortDir, String query);
}
