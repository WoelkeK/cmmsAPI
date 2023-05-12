package pl.medos.cmmsApi.controllers;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.dto.EmployeesImportDto;
import pl.medos.cmmsApi.exception.EmployeeNotFoundException;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.service.DepartmentService;
import pl.medos.cmmsApi.service.EmployeeService;
import pl.medos.cmmsApi.service.ImportService;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/employees")
@SessionAttributes(names = {"employees", "departments"})
public class WebEmployeeController {

    private static final Logger LOGGER = Logger.getLogger(WebEmployeeController.class.getName());
    private String fileName = "c:/XL/sheet6.xlsx";
    private EmployeeService employeeService;
    private DepartmentService departmentService;
    private ImportService importService;

    public WebEmployeeController(EmployeeService employeeService, DepartmentService departmentService, ImportService importService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.importService = importService;
    }

    @GetMapping(value = "/list")
    public String listViewAll(ModelMap modelMap) throws IOException {
        LOGGER.info("listView()");
        List<Employee> employees = employeeService.finadAllEmployees();
        modelMap.addAttribute("employees", employees);
        List<Department> departments = departmentService.findAllDepartments();
        modelMap.addAttribute("departments", departments);
        return "list-employee.html";
    }

    @GetMapping
    public String listView(Model model) throws IOException {
        LOGGER.info("listView()");
        return findPaginated(1, model);
    }

    @GetMapping(value = "/page/{pageNo}")
    public String findPaginated(
            @PathVariable(value = "pageNo") int pageNo,
            Model model) throws IOException {
        int pageSize = 10;
        LOGGER.info("findPage()");
        Page<Employee> pageEmployees = employeeService.findPageinated(pageNo, pageSize);
        List<Employee> employees = pageEmployees.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", pageEmployees.getTotalPages());
        model.addAttribute("totalItems", pageEmployees.getTotalElements());
        model.addAttribute("employees", employees);
        List<Department> departments = departmentService.findAllDepartments();
        model.addAttribute("departments", departments);
        return "list-employee.html";
    }

    @GetMapping("/search/name")
    public String searchEmployeeByName(@RequestParam(value = "employeeName") String query,
                                       Model model) {
        LOGGER.info("search()");
        List<Employee> employeeByName = employeeService.findEmployeeByName(query);
        model.addAttribute("employees", employeeByName);
        return "list-employee";
    }

    @GetMapping(value = "/update/{id}")
    public String updateView(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws Exception {
        LOGGER.info("updateView()");
        Employee employee = employeeService.findEmployeeById(id);
        modelMap.addAttribute("employee", employee);
        return "update-employee.html";
    }

    @PostMapping(value = "/update/{id}")
    public String update(
            @PathVariable(name = "id") Long id,
            @ModelAttribute(name = "employee") Employee employee) throws EmployeeNotFoundException {
        LOGGER.info("update()" + employee);
        Employee updatedEmployee = employeeService.updateEmployee(employee, id);
        LOGGER.info("update(...)" + updatedEmployee);
        return "redirect:/employees";
    }

    @GetMapping(value = "/create")
    public String createView(ModelMap modelMap) {
        LOGGER.info("createView()");
        modelMap.addAttribute("employee", new Employee());
        return "create-employee.html";
    }

    @PostMapping(value = "/create")
    public String create(
            String department,
            @ModelAttribute(name = "employee") Employee employee) {
        LOGGER.info("create(" + employee + ")");
//        LOGGER.info("create(" + lastName + ")");
//        employee.setPassword(passwordEncoder.encode(clientModel.getPassword()));
        employeeService.createEmployee(employee);
        return "redirect:/employees";
    }

    @GetMapping(value = "/read/{id}")
    public String read(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws Exception {
        LOGGER.info("read(" + id + ")");
        Employee employee = employeeService.findEmployeeById(id);
        modelMap.addAttribute("employee", employee);
        return "read-employee.html";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(
            @PathVariable(name = "id") Long id) {
        LOGGER.info("delete()");
        employeeService.deleteEmployee(id);
        return "redirect:/employees";
    }

    @GetMapping("/file")
    public String showUploadForm() {
        return "uploadEmp-form";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {

        LOGGER.info("importEmployees()");
        if (file.isEmpty()) {
            LOGGER.info("Please select file to upload");
            return "redirect/employees";
        }

        EmployeesImportDto employeesImportDto = new EmployeesImportDto();
        List<Employee> readedEmployees = importService.importExcelEmployeesData(file);

        readedEmployees.forEach((employee) -> {
            employeeService.createEmployee(employee);
        });
        LOGGER.info("importEmployees(...) ");

        return "redirect:/employees";
    }
}
