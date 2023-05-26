package pl.medos.cmmsApi.controllers;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.HardwareNotFoundException;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.model.Hardware;
import pl.medos.cmmsApi.service.DepartmentService;
import pl.medos.cmmsApi.service.EmployeeService;
import pl.medos.cmmsApi.service.HardwareService;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/hardwares")
@SessionAttributes(names = {"employees", "departments"})
public class WebHardwareController {

    private static final Logger LOGGER = Logger.getLogger(WebHardwareController.class.getName());
    private HardwareService hardwareService;
    private DepartmentService departmentService;
    private EmployeeService employeeService;

    public WebHardwareController(HardwareService hardwareService, DepartmentService departmentService, EmployeeService employeeService) {
        this.hardwareService = hardwareService;
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public String listViewAll(Model model) {
        LOGGER.info("listViewAll()");
        List<Hardware> hardwares = hardwareService.listAll();
        model.addAttribute("hardwares", hardwares);
        List<Employee> employees = employeeService.finadAllEmployees();
        model.addAttribute("employees", employees);
        List<Department> departments = departmentService.findAllDepartments();
        model.addAttribute("departments", departments);
        LOGGER.info("listViewAll(...)");
        return "list-hardware";
    }
    @GetMapping(value = "/page/{pageNo}")
    public String pageing(@RequestParam(value = "pageNo") int pageNo, Model model){
        LOGGER.info("pageing()");
        int size = 5;
        Page<Hardware> hardwares = hardwareService.pagesHardware(pageNo, size);
        model.addAttribute("hardwares", hardwares);
        LOGGER.info("pageing(...)");
        return "page-hardware";
    }

    @GetMapping("/create")
    public String createView(Model model) {
        LOGGER.info("createView()");
        model.addAttribute("hardware", new Hardware());
        LOGGER.info("createView(...)");
        return "create-Hardware";
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
        LOGGER.info("updateHardware()" + hardware.getId());
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
}
