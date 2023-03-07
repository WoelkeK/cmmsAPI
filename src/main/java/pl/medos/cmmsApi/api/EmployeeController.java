package pl.medos.cmmsApi.api;

import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.service.EmployeeService;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    private static final Logger LOGGER = Logger.getLogger(EmployeeController.class.getName());

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> list() {
        LOGGER.info("list()");
        List<Employee> employeeList = employeeService.finadAllEmployees();
        LOGGER.info("list(...)");
        return employeeList;
    }

    @PostMapping("/employee")
    public Employee create(@RequestBody Employee employee) {
        LOGGER.info("create()");
        Employee createdEmployee = employeeService.createEmployee(employee);
        LOGGER.info("create(...)" + createdEmployee);
        return createdEmployee;
    }

    @PutMapping("/employee")
    public Employee update(@RequestBody Employee employee) {
        LOGGER.info("update()");
        Employee updatedEmployee = employeeService.updateEmployee(employee);
        LOGGER.info("update(...)" + updatedEmployee);
        return updatedEmployee;
    }

    @DeleteMapping("/employee/{id}")
    public void delete(Long id) {
        LOGGER.info("delete()");
        employeeService.deleteEmployee(id);
        LOGGER.info("delete(...)");
    }
}
