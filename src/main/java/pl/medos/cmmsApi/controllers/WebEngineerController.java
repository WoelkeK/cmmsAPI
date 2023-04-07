package pl.medos.cmmsApi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.EmployeeNotFoundException;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Engineer;
import pl.medos.cmmsApi.service.DepartmentService;
import pl.medos.cmmsApi.service.EngineerService;
import pl.medos.cmmsApi.service.ImportService;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/engineers")
@SessionAttributes(names = {"engineers", "departments"})
public class WebEngineerController {

    private static final Logger LOGGER = Logger.getLogger(WebEngineerController.class.getName());
    private String fileName = "c:/XL/sheet6.xlsx";
    private EngineerService engineerService;
    private DepartmentService departmentService;
    private ImportService importService;

    public WebEngineerController(EngineerService engineerService, DepartmentService departmentService, ImportService importService) {
        this.engineerService = engineerService;
        this.departmentService = departmentService;
        this.importService = importService;
    }

    @GetMapping
    public String listView(ModelMap modelMap) throws IOException {
        LOGGER.info("listView()");
        List<Engineer> engineers = engineerService.finadAllEmployees();
        modelMap.addAttribute("engineers", engineers);
        List<Department> departments = departmentService.findAllDepartments();
        modelMap.addAttribute("departments", departments);
        return "list-engineer.html";
    }

    @GetMapping("/search/name")
    public String searchEmployeeByName(@RequestParam(value = "employeeName") String query,
                                       Model model) {
        LOGGER.info("search()");
        Engineer employeeByName = engineerService.findEmployeeByName(query);
        model.addAttribute("engineers", employeeByName);
        return "list-engineer";
    }

    @GetMapping(value = "/update/{id}")
    public String updateView(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws Exception {
        LOGGER.info("updateView()");
        Engineer engineer = engineerService.findEmployeeById(id);
        modelMap.addAttribute("engineer", engineer);
        return "update-engineer.html";
    }

    @PostMapping(value = "/update/{id}")
    public String update(
            @PathVariable(name = "id") Long id,
            @ModelAttribute(name = "employee") Engineer employee) throws EmployeeNotFoundException {
        LOGGER.info("update()" + employee);
        Engineer updatedEmployee = engineerService.updateEmployee(employee, id);
        LOGGER.info("update(...)" + updatedEmployee);
        return "redirect:/engineers";
    }

    @GetMapping(value = "/create")
    public String createView(ModelMap modelMap) {
        LOGGER.info("createView()");
        modelMap.addAttribute("employee", new Engineer());
        return "create-engineer.html";
    }

    @PostMapping(value = "/create")
    public String create(
            String department,
            @ModelAttribute(name = "employee") Engineer employee) {
        LOGGER.info("create(" + employee + ")");
//        LOGGER.info("create(" + lastName + ")");
//        employee.setPassword(passwordEncoder.encode(clientModel.getPassword()));
        engineerService.createEmployee(employee);
        return "redirect:/engineers";
    }

    @GetMapping(value = "/read/{id}")
    public String read(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws Exception {
        Engineer employee = engineerService.findEmployeeById(id);
        modelMap.addAttribute("employee", employee);
        return "read-engineer.html";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(
            @PathVariable(name = "id") Long id) {
        LOGGER.info("delete()");
        engineerService.deleteEmployee(id);
        return "redirect:/engineers";
    }

//    @GetMapping("/file")
//    public String showUploadForm() {
//        return "uploadEmp-form";
//    }
//
//    @PostMapping("/upload")
//    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
//
//        LOGGER.info("importEmployees()");
//        if (file.isEmpty()) {
//            LOGGER.info("Please select file to upload");
//            return "redirect/employees";
//        }

//        EmployeesImportDto employeesImportDto = new EmployeesImportDto();
//        List<Engineer> readedEmployees = importService.importExcelEmployeesData(file);
//
//        readedEmployees.forEach((employee) -> {
//            employeeService.createEmployee(employee);
//        });
//        LOGGER.info("importEmployees(...) ");
//
//        return "redirect:/employees";
//    }
//    }
}
