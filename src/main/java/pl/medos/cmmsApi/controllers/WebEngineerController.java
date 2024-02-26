package pl.medos.cmmsApi.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
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
import pl.medos.cmmsApi.model.Engineer;
import pl.medos.cmmsApi.service.DepartmentService;
import pl.medos.cmmsApi.service.EngineerService;
import pl.medos.cmmsApi.service.ExportService;
import pl.medos.cmmsApi.util.imports.ImportEmployee;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private ModelMapper mapper;
    private ExportService exportService;

    private ImportEmployee importEmployee;

    public WebEngineerController(EngineerService engineerService, DepartmentService departmentService, ModelMapper mapper, ExportService exportService, ImportEmployee importEmployee) {
        this.engineerService = engineerService;
        this.departmentService = departmentService;
        this.mapper = mapper;
        this.exportService = exportService;
        this.importEmployee = importEmployee;
    }

    @GetMapping("/list")
    public String listView(ModelMap modelMap) throws IOException {
        LOGGER.info("listView()");
        List<Engineer> engineers = engineerService.finadAllEngineers();
        modelMap.addAttribute("engineers", engineers);
        List<Department> departments = departmentService.findAllDepartments();
        modelMap.addAttribute("departments", departments);
        return "list-engineer.html";
    }

    @GetMapping
    public String listView(
            @RequestParam(name = "pageNo", defaultValue = "1") int page,
//            @RequestParam(defaultValue = "10") int pageSize,
            Model model) throws IOException {
        LOGGER.info("listView()");
        return findPaginated(page, "name", "desc", model);
    }

    @GetMapping(value = "/page/{pageNo}")
    public String findPaginated(
            @PathVariable(value = "pageNo") int pageNo,
            @RequestParam(name = "sortField") String sortField,
            @RequestParam(name = "sortDir") String sortDir,
//            @PathVariable(value = "pageSize") int pageSize,
            Model model) throws IOException {
        int pageSize = 10;
        LOGGER.info("findPage()" + pageNo + " " + sortField + " " + sortDir);
        Page<Engineer> pageEngineers = engineerService.findPageinated(pageNo, pageSize, sortField, sortDir);
        List<Engineer> engineers = pageEngineers.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", pageEngineers.getTotalPages());
        model.addAttribute("totalItems", pageEngineers.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("engineers", engineers);
        List<Department> departments = departmentService.findAllDepartments();
        model.addAttribute("departments", departments);
        return "main-engineer";
    }

    @GetMapping(value = "/search/name")
    public String findEngineerByname(
            @RequestParam(value = "engineerName") String query,
            Model model) throws IOException {
        int pageSize = 10;
        int pageNo = 1;
        String sortField = "name";
        String sortDir = "desc";

        LOGGER.info("findPage()");
        Page<Engineer> engineerPage = engineerService.findPageinatedQuery(pageNo, pageSize, sortField, sortDir, query);
        List<Engineer> engineers = engineerPage.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", engineerPage.getTotalPages());
        model.addAttribute("totalItems", engineerPage.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("employees", engineers);
        List<Department> departments = departmentService.findAllDepartments();
        model.addAttribute("departments", departments);
        return "main-engineer";
    }

    @GetMapping(value = "/update/{id}")
    public String updateView(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "pageNo") int pageNo,
            ModelMap modelMap) throws Exception {
        LOGGER.info("updateView()");
        Engineer engineer = engineerService.findEngineerById(id);
        modelMap.addAttribute("engineer", engineer);
        modelMap.addAttribute("pageNo", pageNo);
        return "update-engineer.html";
    }

    @PostMapping(value = "/update/{id}")
    public String update(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "pageNo") int pageNo,
            @ModelAttribute(name = "engineer") Engineer engineer) throws EmployeeNotFoundException {
        LOGGER.info("update()" + engineer);
        Engineer updatedEngineer = engineerService.updateEngineer(engineer, id);
        LOGGER.info("update(...)" + updatedEngineer);
        return "redirect:/engineers?pageNo=" + pageNo;
    }

    @GetMapping(value = "/create")
    public String createView(ModelMap modelMap) {
        LOGGER.info("createView()");
        modelMap.addAttribute("engineer", new Engineer());
        return "create-engineer.html";
    }

    @PostMapping(value = "/create")
    public String create(
            String department,
            @ModelAttribute(name = "engineer") Engineer engineer) {
        LOGGER.info("create(" + engineer + ")");
//        LOGGER.info("create(" + lastName + ")");
//        employee.setPassword(passwordEncoder.encode(clientModel.getPassword()));
        engineerService.createEngineer(engineer);
        return "redirect:/engineers";
    }

    @GetMapping(value = "/read/{id}")
    public String read(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws Exception {
        Engineer engineer = engineerService.findEngineerById(id);
        modelMap.addAttribute("engineer", engineer);
        return "read-engineer.html";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(
            @RequestParam(name = "pageNo") int pageNo,
            @PathVariable(name = "id") Long id) {
        LOGGER.info("delete()");
        engineerService.deleteEngineer(id);
        return "redirect:/engineers";
    }

    @GetMapping("/search/query")
    public String searchEmployeeByName(
            @RequestParam(value = "query") String query,
            @RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
            Model model) {
        LOGGER.info("search()");
        int pagesize = 10;
        Page<Engineer> engineerByName = engineerService.findEngineerByName(pageNo, pagesize, query);
        LOGGER.info("findEngineerByName()");
        model.addAttribute("engineer", engineerByName);
        model.addAttribute("currentPage", pageNo);
        return "main-engineer";
    }

    @GetMapping("/deleteAll")
    public void deleteAll() {
        LOGGER.info("deleteAll");
        engineerService.deleteAll();
    }


    @GetMapping("/file")
    public String showUploadForm() {
        return "uploadEng-form";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        LOGGER.info("importEngineers()");
        if (file.isEmpty()) {
            LOGGER.info("Please select file to upload");
            return "redirect:/engineers";
        }
        List<Employee> employees = importEmployee.importExcelEmployeesData(file);
        List<Engineer> engineers = employees.stream().map(employee -> mapper.map(employee, Engineer.class)).toList();

        engineers.forEach((engineer) -> {
            engineerService.createEngineer(engineer);
        });
        LOGGER.info("importEmployees(...) ");
        return "redirect:/employees";
    }

    @GetMapping(value = "/export")
    public void exportEngineers(@ModelAttribute(name = "engineers") List<Engineer> engineers,
                                HttpServletResponse response, Model model) throws Exception {
        LOGGER.info("export()");

        engineers = engineerService.finadAllEngineers();
        response.setContentType("application/octet-stream");
        DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateTimeFormat.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=employee" + currentDateTime + ".xlsx";

        List<Employee> employees = engineers.stream().map(engineer -> mapper.map(engineer, Employee.class)).toList();
        response.setHeader(headerKey, headerValue);
        exportService.excelEmployeeModelGenerator(employees);
        exportService.generateExcelEmployeeFile(response);
        response.flushBuffer();
        LOGGER.info("export(...)");
    }

}
