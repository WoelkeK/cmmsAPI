package pl.medos.cmmsApi.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.exception.EmployeeNotFoundException;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.service.DepartmentService;
import pl.medos.cmmsApi.service.EmployeeService;
import pl.medos.cmmsApi.service.ExportService;
import pl.medos.cmmsApi.util.imports.ImportEmployee;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/employees")
@SessionAttributes(names = {"employees", "departments"})
@Slf4j
public class WebEmployeeController {

    private EmployeeService employeeService;
    private DepartmentService departmentService;
    private ImportEmployee importEmployee;
    private ExportService exportService;

    public WebEmployeeController(EmployeeService employeeService, DepartmentService departmentService, ImportEmployee importEmployee, ExportService exportService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.importEmployee = importEmployee;
        this.exportService = exportService;
    }

    @Value("${name1}")
    private String name1;
    @Value("${link1}")
    private String link1;

    @Value("${name2}")
    private String name2;
    @Value("${link2}")
    private String link2;

    @Value("${name3}")
    private String name3;
    @Value("${link3}")
    private String link3;

    @Value("${name4}")
    private String name4;
    @Value("${link4}")
    private String link4;

    @Value("${name5}")
    private String name5;
    @Value("${link5}")
    private String link5;

    @Value("${name6}")
    private String name6;
    @Value("${link6}")
    private String link6;


    @GetMapping(value = "/list")
    public String listViewAll(ModelMap modelMap) {
        log.debug("listView()");
        List<Employee> employees = employeeService.finadAllEmployees();
        modelMap.addAttribute("employees", employees);
        List<Department> departments = departmentService.findAllDepartments();
        modelMap.addAttribute("departments", departments);
        return "list-employees";
    }

    @GetMapping
    public String listView(
            @RequestParam(name = "pageNo", defaultValue = "1") int page,
            Model model) throws IOException {
        log.debug("listView()");
        return findPaginated(page, "name", "desc",model);
    }

    @GetMapping(value = "/page/{pageNo}")
    public String findPaginated(
            @PathVariable(value = "pageNo") int pageNo,
            @RequestParam(name = "sortField") String sortField,
            @RequestParam(name = "sortDir") String sortDir,
            Model model) throws IOException {
        int pageSize = 10;
        log.debug("findPage()");

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
        model.addAttribute("link1", link1);
        model.addAttribute("link2", link2);
        model.addAttribute("link3", link3);
        model.addAttribute("link4", link4);
        model.addAttribute("link5", link5);
        model.addAttribute("link6", link6);
        model.addAttribute("name1", name1);
        model.addAttribute("name2", name2);
        model.addAttribute("name3", name3);
        model.addAttribute("name4", name4);
        model.addAttribute("name5", name5);
        model.addAttribute("name6", name6);
        return "main-employees";
    }

    @GetMapping(value = "/search/name")
    public String findEmployeeByname(
            @RequestParam(value = "employeeName") String query,
            Model model) throws IOException {
        int pageSize=10;
        int pageNo=1;
        String sortField="name";
        String sortDir="desc";

        log.debug("findPage()");
        Page<Employee> employeePage = employeeService.findPageinatedQuery(pageNo, pageSize, sortField, sortDir, query);
        List<Employee> employees = employeePage.getContent();
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
        log.debug("updateView()" + pageNo);
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
        Employee updatedEmployee = employeeService.updateEmployee(employee, id);
        log.debug("update(...)" + updatedEmployee);
        return "redirect:/employees?pageNo=" + pageNo;
    }

    @GetMapping(value = "/create")
    public String createView(ModelMap modelMap) {
        log.debug("createView()");
        modelMap.addAttribute("employee", new Employee());
        return "create-employee";
    }

    @PostMapping(value = "/create")
    public String create(
            String department,
            @ModelAttribute(name = "employee") Employee employee) {
        log.debug("create(" + employee + ")");
        employeeService.createEmployee(employee);
        return "redirect:/employees";
    }

    @GetMapping(value = "/read/{id}")
    public String read(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws Exception {
        log.debug("read(" + id + ")");
        Employee employee = employeeService.findEmployeeById(id);
        modelMap.addAttribute("employee", employee);
        return "read-employee";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(
            @RequestParam(name = "pageNo") int pageNo,
            @PathVariable(name = "id") Long id) {
        log.debug("delete()");
        employeeService.deleteEmployee(id);
        return "redirect:/employees?pageNo=" + pageNo;
    }

    @GetMapping("/file")
    public String showUploadForm() {
        return "uploadEmp-form";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        log.debug("importEmployees()");
        if (file.isEmpty()) {
            log.debug("Please select file to upload");
            return "redirect:/employees";
        }
        List<Employee> employees = importEmployee.importExcelEmployeesData(file);
        employees.forEach((employee) -> {
            employeeService.createEmployee(employee);
        });
        log.debug("importEmployees(...) ");
        return "redirect:/employees";
    }

    @GetMapping(value = "/export")
    public void exportEmployees(@ModelAttribute(name = "employees") List<Employee> employees,
                                HttpServletResponse response, Model model) throws Exception {
        log.debug("export()");
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
        log.debug("export(...)");
    }

    @GetMapping("/search/query")
    public String searchEmployeeByName(
            @RequestParam(value = "query") String query,
            @RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
            Model model){
       log.debug("findEmployeeByName()");
        int pagesize =10;
        Page<Employee> employeeByName = employeeService.findEmployeeByName(pageNo, pagesize, query);
        model.addAttribute("employees", employeeByName);
        model.addAttribute("currentPage", pageNo);
        return "main-employees";
    }

    @GetMapping("/deleteAll")
    public void deleteAll(){
        log.debug("deleteAll");
        employeeService.deleteAll();
    }
}
