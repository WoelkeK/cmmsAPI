package pl.medos.cmmsApi.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.EmployeeNotFoundException;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.service.EmployeeService;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/employees/")
@Slf4j
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> list() {
        log.debug("list()");
        List<Employee> employeeList = employeeService.finadAllEmployees();
        log.debug("list(...)");
        return employeeList;
    }

    @PostMapping("/create")
    public Employee create(@RequestBody Employee employee) {
        log.debug("create()");
        Employee createdEmployee = employeeService.createEmployee(employee);
        log.debug("create(...)" + createdEmployee);
        return createdEmployee;
    }

    @PutMapping("/update/{id}")
    public Employee update(@PathVariable(name = "id") Long id,
                           @RequestBody Employee employee) throws EmployeeNotFoundException {
        log.debug("update()");
        Employee updatedEmployee = employeeService.updateEmployee(employee, id);
        log.debug("update(...)" + updatedEmployee);
        return updatedEmployee;
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable(name = "id")Long id) {
        log.debug("delete()");
        employeeService.deleteEmployee(id);
        log.debug("delete(...)");
    }

    @GetMapping("/findByName/{name}")
    public List<Employee> findEmployeeByName(@PathVariable(name = "name") String query) {
        log.debug("findEmployeeByName");
        return employeeService.findEmployeeByRawName(query);
    }
}
