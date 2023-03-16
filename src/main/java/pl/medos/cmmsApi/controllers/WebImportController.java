package pl.medos.cmmsApi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.model.Person;
import pl.medos.cmmsApi.service.EmployeeService;
import pl.medos.cmmsApi.service.ImportService;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/imports")
public class WebImportController {

    private static final Logger LOGGER = Logger.getLogger(WebImportController.class.getName());

    private ImportService importService;
    private EmployeeService employeeService;

    private String fileName= "c:/XL/sheet3.xlsx";

    public WebImportController(ImportService importService, EmployeeService employeeService) {
        this.importService = importService;
        this.employeeService = employeeService;
    }

    @GetMapping(value = "/read")
    public String createView(Model model) throws IOException {
        LOGGER.info("import person)");
        List<Employee> employees = importService.importExcelData(fileName);
        model.addAttribute("employees", employees);
        LOGGER.info("Person from file: " + employees);
        return "import.html";
    }

    @PostMapping(value = "/save")
    public String saveImportedEmployees(Model model) throws IOException {
        LOGGER.info("saveImportedEmployees()");
        List<Employee> employees = importService.importExcelData(fileName);

        employees.forEach((employee)->{
        employeeService.createEmployee(employee);
        });
        LOGGER.info("saveImportedEmployees() " + employees);
        return "import";
    }

    @PostMapping(value = "/persons")
    public String create(){
        LOGGER.info("persons()");
        LOGGER.info("persons(...)");
        return "redirect:/imports";
    }
}
