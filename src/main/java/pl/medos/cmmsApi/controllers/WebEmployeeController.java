package pl.medos.cmmsApi.controllers;

import jakarta.servlet.http.HttpServletResponse;
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
import pl.medos.cmmsApi.model.Hardware;
import pl.medos.cmmsApi.model.Machine;
import pl.medos.cmmsApi.service.DepartmentService;
import pl.medos.cmmsApi.service.EmployeeService;
import pl.medos.cmmsApi.service.ExportService;
import pl.medos.cmmsApi.service.ImportService;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private ExportService exportService;

    public WebEmployeeController(EmployeeService employeeService, DepartmentService departmentService, ImportService importService, ExportService exportService) {

        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.importService = importService;
        this.exportService = exportService;
    }

    @GetMapping(value = "/list")
    public String listViewAll(ModelMap modelMap) throws IOException {
        LOGGER.info("listView()");
        List<Employee> employees = employeeService.finadAllEmployees();
        modelMap.addAttribute("employees", employees);
        List<Department> departments = departmentService.findAllDepartments();
        modelMap.addAttribute("departments", departments);
        return "list-employees";
    }

    @GetMapping
    public String listView(
            @RequestParam(name = "pageNo", defaultValue = "1") int page,
//            @RequestParam(defaultValue = "10") int pageSize,
            Model model) throws IOException {
        LOGGER.info("listView()");
        return findPaginated(page, "name", "desc",model);
    }

    @GetMapping(value = "/page/{pageNo}")
    public String findPaginated(
            @PathVariable(value = "pageNo") int pageNo,
            @RequestParam(name = "sortField") String sortField,
            @RequestParam(name = "sortDir") String sortDir,
//            @PathVariable(value = "pageSize") int pageSize,
            Model model) throws IOException {
        int pageSize = 10;
        LOGGER.info("findPage()");
        Page<Employee> pageEmployees = employeeService.findPageinated(pageNo, pageSize, sortField, sortDir);
        List<Employee> employees = pageEmployees.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", pageEmployees.getTotalPages());
        model.addAttribute("totalItems", pageEmployees.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("employees", employees);
        List<Department> departments = departmentService.findAllDepartments();
        model.addAttribute("departments", departments);
        return "main-employees";
    }

    @GetMapping(value = "/search/name")
    public String findEmployeeByname(
            @RequestParam(value = "employeeName") String query,
//            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            Model model) throws IOException {
        int pageSize=10;
        int pageNo=1;
        String sortField="name";
        String sortDir="desc";

        LOGGER.info("findPage()");
        Page<Employee> employeePage = employeeService.findPageinatedQuery(pageNo, pageSize, sortField, sortDir, query);
        List<Employee> employees = employeePage.getContent();
//        List<Employee> employeeByRawName = employeeService.findEmployeeByRawName(query);

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", employeePage.getTotalPages());
        model.addAttribute("totalItems", employeePage.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("employees", employees);
        List<Department> departments = departmentService.findAllDepartments();
        model.addAttribute("departments", departments);
        return "main-employees";
    }

    @GetMapping(value = "/update/{id}")
    public String updateView(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "pageNo") int pageNo,
            ModelMap modelMap) throws Exception {
        LOGGER.info("updateView()" + pageNo);
        Employee employee = employeeService.findEmployeeById(id);
        modelMap.addAttribute("employee", employee);
        modelMap.addAttribute("pageNo", pageNo);
        return "update-employee";
    }

    @PostMapping(value = "/update/{id}")
    public String update(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "pageNo") int pageNo,
            @ModelAttribute(name = "employee") Employee employee) throws EmployeeNotFoundException {
        LOGGER.info("update()" + pageNo);
        Employee updatedEmployee = employeeService.updateEmployee(employee, id);
        LOGGER.info("update(...)" + updatedEmployee);
        return "redirect:/employees?pageNo=" + pageNo;
    }

    @GetMapping(value = "/create")
    public String createView(ModelMap modelMap) {
        LOGGER.info("createView()");
        modelMap.addAttribute("employee", new Employee());
        return "create-employee";
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
        return "read-employee";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(
            @RequestParam(name = "pageNo") int pageNo,
            @PathVariable(name = "id") Long id) {
        LOGGER.info("delete()");
        employeeService.deleteEmployee(id);
        return "redirect:/employees?pageNo=" + pageNo;
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
            return "redirect:/employees";
        }

        EmployeesImportDto employeesImportDto = new EmployeesImportDto();
        List<Employee> readedEmployees = importService.importExcelEmployeesData(file);
        readedEmployees.forEach((employee) -> {
            employeeService.createEmployee(employee);
        });
        LOGGER.info("importEmployees(...) ");
        return "redirect:/employees";
    }

    @GetMapping(value = "/export")
    public void exportEmployees(@ModelAttribute(name = "employees") List<Employee> employees,
                                HttpServletResponse response, Model model) throws Exception {
        LOGGER.info("export()");

        employees = employeeService.finadAllEmployees();
        response.setContentType("application/octet-stream");
        DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateTimeFormat.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=employee" + currentDateTime + ".xlsx";

        response.setHeader(headerKey, headerValue);
        exportService.excelEmployeeModelGenerator(employees);
        exportService.generateExcelEmployeeFile(response);
        response.flushBuffer();
        LOGGER.info("export(...)");
    }


    @GetMapping("/search/query")
    public String searchEmployeeByName(
            @RequestParam(value = "query") String query,
            @RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
            Model model){
        LOGGER.info("search()");
        int pagesize =10;
        Page<Employee> employeeByName = employeeService.findEmployeeByName(pageNo, pagesize, query);
        LOGGER.info("findEmployeeByName()");
        model.addAttribute("employees", employeeByName);
        model.addAttribute("currentPage", pageNo);
        return "main-employees";
    }

    @GetMapping("/deleteAll")
    public void deleteAll(){
        LOGGER.info("deleteAll");
        employeeService.deleteAll();

    }
}
