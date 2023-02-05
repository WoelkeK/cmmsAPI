package pl.medos.cmmsApi.controllers;

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

    @GetMapping("/employee")
    public List<Employee> list() {
        LOGGER.info("list()");
        List<Employee> employeeList = employeeService.list();
        LOGGER.info("list(...)");
        return employeeList;
    }

    @PostMapping("/employee")
    public Employee create(@RequestBody Employee employee) {
        LOGGER.info("create()");
        Employee createdEmployee = employeeService.create(employee);
        LOGGER.info("create(...)" + createdEmployee);
        return createdEmployee;
    }

    @GetMapping("/employee/{id}")
    public Employee read(Long id) {
        LOGGER.info("read(" + id + ")");
        Employee readedEmployee = employeeService.read(id);
        LOGGER.info("read(...)" + readedEmployee);
        return readedEmployee;
    }

    @PutMapping("/employee")
    public Employee update(@RequestBody Employee employee) {
        LOGGER.info("update()");
        Employee updatedEmployee = employeeService.update(employee);
        LOGGER.info("update(...)" + updatedEmployee);
        return updatedEmployee;
    }

    @DeleteMapping("/employee/{id}")
    public String delete(Long id) {
        LOGGER.info("delete()");
        String deleteMessage = employeeService.delete(id);
        LOGGER.info("delete(...)");
        return deleteMessage;
    }
}
