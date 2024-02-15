package pl.medos.cmmsApi.api;

import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.EmployeeNotFoundException;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.service.EmployeeService;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/employees/")
public class EmployeeController {

    private static final Logger LOGGER = Logger.getLogger(EmployeeController.class.getName());

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> list() {
        LOGGER.info("list()");
        List<Employee> employeeList = employeeService.finadAllEmployees();
        LOGGER.info("list(...)");
        return employeeList;
    }

    @PostMapping("/create")
    public Employee create(@RequestBody Employee employee) {
        LOGGER.info("create()");
        Employee createdEmployee = employeeService.createEmployee(employee);
        LOGGER.info("create(...)" + createdEmployee);
        return createdEmployee;
    }

    @PutMapping("/update/{id}")
    public Employee update(@PathVariable(name = "id") Long id,
                           @RequestBody Employee employee) throws EmployeeNotFoundException {
        LOGGER.info("update()");
        Employee updatedEmployee = employeeService.updateEmployee(employee, id);
        LOGGER.info("update(...)" + updatedEmployee);
        return updatedEmployee;
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable(name = "id")Long id) {
        LOGGER.info("delete()");
        employeeService.deleteEmployee(id);
        LOGGER.info("delete(...)");
    }
    @GetMapping("/findByName/{name}")
    public List<Employee> findEmployeeByName(@PathVariable(name = "name") String query) {
        LOGGER.info("findEmployeeByName");
        return employeeService.findEmployeeByRawName(query);
    }
}
