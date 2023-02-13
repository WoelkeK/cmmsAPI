package pl.medos.cmmsApi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.service.DepartmentService;
import pl.medos.cmmsApi.service.EmployeeService;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/employees")
public class WebEmployeeController {

    private static final Logger LOGGER = Logger.getLogger(WebEmployeeController.class.getName());
    private EmployeeService employeeService;
    private DepartmentService departmentService;

    public WebEmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @GetMapping
    public String listView(ModelMap modelMap) {
        LOGGER.info("listView()");
        List<Employee> employees = employeeService.list();
        modelMap.addAttribute("employees", employees);
        return "list-employee.html";
    }

    @GetMapping(value = "/update/{id}")
    public String updateView(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws Exception {
        LOGGER.info("updateView()");
        Employee employee = employeeService.read(id);
        modelMap.addAttribute("employee", employee);
        List<Department> departments = departmentService.list();
        modelMap.addAttribute("departments", departments);
        return "update-employee.html";
    }

    @PostMapping(value = "/update")
    public String update(
            @ModelAttribute(name = "employee") Employee employee) {
        LOGGER.info("update()" + employee);
        Employee updatedEmployee = employeeService.update(employee);
        LOGGER.info("update(...)" + updatedEmployee);
        return "redirect:/employees";
    }

    @GetMapping(value = "/create")
    public String createView(ModelMap modelMap) {
        LOGGER.info("createView()");
        modelMap.addAttribute("employee", new Employee());
        List<Department> departments = departmentService.list();
        modelMap.addAttribute("departments", departments);
        return "create-employee.html";
    }

    @PostMapping(value = "/create")
//    public String create(String firstName, String lastName) {
    public String create(
            String department,
            @ModelAttribute(name = "employee") Employee employee) {
        LOGGER.info("create(" + employee + ")");
//        LOGGER.info("create(" + lastName + ")");
//        employee.setPassword(passwordEncoder.encode(clientModel.getPassword()));
        employeeService.create(employee);
        return "redirect:/employees";
    }

    @GetMapping(value = "/read/{id}")
    public String read(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws Exception {
        LOGGER.info("read(" + id + ")");
        Employee employee = employeeService.read(id);
        modelMap.addAttribute("employee", employee);
        return "read-employee.html";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(
            @PathVariable(name = "id") Long id) {
        LOGGER.info("delete()");
        employeeService.delete(id);
        return "redirect:/employees";
    }
}
