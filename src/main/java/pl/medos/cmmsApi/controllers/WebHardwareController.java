package pl.medos.cmmsApi.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.dto.EmployeesImportDto;
import pl.medos.cmmsApi.exception.HardwareNotFoundException;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.model.Hardware;
import pl.medos.cmmsApi.model.Software;
import pl.medos.cmmsApi.service.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/hardwares")
@SessionAttributes(names = {"employees", "departments", "hardwares", "softwares"})
public class WebHardwareController {

    private static final Logger LOGGER = Logger.getLogger(WebHardwareController.class.getName());
    private HardwareService hardwareService;
    private DepartmentService departmentService;
    private EmployeeService employeeService;
    private SoftwareService softwareService;
    private ExportService exportService;
    private ImportService importService;

    public WebHardwareController(HardwareService hardwareService, DepartmentService departmentService, EmployeeService employeeService, SoftwareService softwareService, ExportService exportService, ImportService importService) {
        this.hardwareService = hardwareService;
        this.departmentService = departmentService;
        this.employeeService = employeeService;
        this.softwareService = softwareService;
        this.exportService = exportService;
        this.importService = importService;
    }

    @GetMapping
    public String listViewAll(Model model) {
        LOGGER.info("listViewAll()");
        List<Hardware> hardwares = hardwareService.listAll();
        model.addAttribute("hardwares", hardwares);
        List<Employee> employees = employeeService.finadAllEmployees();
        model.addAttribute("employees", employees);
        List<Department> departments = departmentService.findAllDepartments();
        List<Software> softwares = softwareService.listAllSoftware();
        model.addAttribute("softwares", softwares);
        model.addAttribute("departments", departments);
        LOGGER.info("listViewAll(...)");
        return "list-hardware";
    }
    @GetMapping(value = "/page/{pageNo}")
    public String pageing(@RequestParam(value = "pageNo") int pageNo, Model model){
        LOGGER.info("pageing()");
        int size = 5;
        Page<Hardware> hardwares = hardwareService.pagesHardware(pageNo, size);
        List<Employee> employees = employeeService.finadAllEmployees();
        model.addAttribute("employees", employees);
        List<Department> departments = departmentService.findAllDepartments();
        model.addAttribute("departments", departments);
        List<Software> softwares = softwareService.listAllSoftware();
        model.addAttribute("softwares", softwares);

        model.addAttribute("hardwares", hardwares);
        LOGGER.info("pageing(...)");
        return "page-hardware";
    }

    @GetMapping("/create")
    public String createView(Model model) {
        LOGGER.info("createView()");
        model.addAttribute("hardware", new Hardware());
        LOGGER.info("createView(...)");
        return "create-hardware";
    }

    @PostMapping("/create")
    public String createHardware(
            @ModelAttribute(name = "hardware") Hardware hardware) {
        LOGGER.info("createHardware()" + hardware.getInstallDate());
        Hardware savedHardware = hardwareService.create(hardware);
        LOGGER.info("createHardware(...)" + savedHardware);
        return "redirect:/hardwares";
    }

    @GetMapping(value = "/read/{id}")
    public String findHardware(@PathVariable(name = "id") Long id,
                               Model model) throws HardwareNotFoundException {
        LOGGER.info("findHardware(" + id + ")");
        Hardware findHardware = hardwareService.read(id);
        model.addAttribute("hardware", findHardware);
        LOGGER.info("findHardware(...)" + findHardware);
        return "view-hardware";
    }

    @GetMapping("/update/{id}")
    public String updateView(@PathVariable(name = "id") Long id, Model model) throws HardwareNotFoundException {
        LOGGER.info("updateView(" + id + ")");
        Hardware hardware = hardwareService.read(id);
        model.addAttribute("hardware", hardware);
        LOGGER.info("updateView(...)"+ hardware.getId());
        return "update-hardware";
    }

    @PostMapping("/update")
    public String updateHardware(
            @ModelAttribute(name = "hardware") Hardware hardware) throws HardwareNotFoundException {
        LOGGER.info("updateHardware(emp)" + hardware.getId());
        Hardware updatedHardware = hardwareService.update(hardware);
        LOGGER.info("updateHardware(...)");
        return "redirect:/hardwares";
    }

    @GetMapping("/delete/{id}")
    public String deleteHardware(@PathVariable(name = "id") Long id) {
        LOGGER.info("deleteHardware(" + id + ")");
        hardwareService.delete(id);
        LOGGER.info("deleteHardware(...)");
        return "redirect:/hardwares";
    }


    @GetMapping(value = "/export")
    public void exportMachines(@ModelAttribute(name = "hardware") List<Hardware> hardwares,
                               HttpServletResponse response, Model model) throws Exception {
        LOGGER.info("export()");
        response.setContentType("application/octet-stream");
        DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateTimeFormat.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=hardware" + currentDateTime + ".xlsx";

        response.setHeader(headerKey, headerValue);
        exportService.excelHardwaresModelGenerator(hardwares);
        exportService.generateExcelFile(response);
        response.flushBuffer();
        LOGGER.info("export(...)");
    }

    @GetMapping(value = "/file")
    public String showUploadForm() {
        return "uploadMach-form";
    }

    @PostMapping(value = "/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {

        LOGGER.info("importHardwares()");
        if (file.isEmpty()) {
            LOGGER.info("Please select file to upload");
            return "redirect/hardwares";
        }

        EmployeesImportDto employeesImportDto = new EmployeesImportDto();
        List<Hardware> hardwares = importService.importExcelHardwareeData(file);

        hardwares.forEach((hardware) -> {
            hardwareService.create(hardware);
        });
        LOGGER.info("importHardwares(...) ");
        return "redirect:/hardwares";
    }

    @GetMapping(value = "/search/query")
    public String searchHardwareByQuery(@RequestParam(value = "query") String query,
                                       Model model) {
        LOGGER.info("search()");
        List<Hardware> hardwares = hardwareService.findHardwaresByQuery(query);
        model.addAttribute("hardwares", hardwares);
        return "list-hardware";
    }
}
