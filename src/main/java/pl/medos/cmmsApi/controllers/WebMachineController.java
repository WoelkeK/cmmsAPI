package pl.medos.cmmsApi.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.DepartmentNotFoundException;
import pl.medos.cmmsApi.exception.MachineNotFoundException;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Machine;
import pl.medos.cmmsApi.service.DepartmentService;
import pl.medos.cmmsApi.service.ExportService;
import pl.medos.cmmsApi.service.MachineService;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/machines")
@SessionAttributes(names = {"departments", "machines"})
public class WebMachineController {

    private static final Logger LOGGER = Logger.getLogger(WebMachineController.class.getName());

    private MachineService machineService;
    private DepartmentService departmentService;
    private ExportService exportService;

    public WebMachineController(MachineService machineService, DepartmentService departmentService, ExportService exportService) {
        this.machineService = machineService;
        this.departmentService = departmentService;
        this.exportService = exportService;
    }

    @GetMapping
    public String listView(Model model) {
        LOGGER.info("listView()");
        List<Department> departments = departmentService.findAllDepartments();
        model.addAttribute("departments", departments);
        List<Machine> machines = machineService.findAllMachines();
        model.addAttribute("machines", machines);
        LOGGER.info("listView(...)" + machines);
        return "list-machine";
    }

    @GetMapping("/search/name")
    public String searchMachineByName(@RequestParam(value = "machineName") String query,
                                      Model model) {
        LOGGER.info("search()");
        List<Machine> machines = machineService.findMachinesByName(query);
        model.addAttribute("machines", machines);
        return "list-machine";
    }

    @GetMapping("/search/machine")
    public String searchJobsByMachineId(@RequestParam(value = "machineName") String machineName,
                                      Model model) throws MachineNotFoundException {
        LOGGER.info("search()");
        Machine machines = machineService.findMachineById(Long.parseLong(machineName));
        model.addAttribute("machines", machines);
        return "list-machine";
    }

    @GetMapping("/search/department")
    public String searchJMachineByDepartment(@RequestParam(value = "machineDepartment") String departmentName,
                                             Model model) throws DepartmentNotFoundException {
        LOGGER.info("search()");
        Department departmentByName = departmentService.findDepartmentById(Long.parseLong(departmentName));
        List<Machine> machines = machineService.findMachinesByDepartment(departmentByName);
        model.addAttribute("machines", machines);
        return "list-machine";
    }

    @GetMapping(value = "/update/{id}")
    public String updateView(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws Exception {
        LOGGER.info("updateView()");
        Machine machine = machineService.findMachineById(id);
        modelMap.addAttribute("machine", machine);
        List<Department> departments = departmentService.findAllDepartments();
        modelMap.addAttribute("departments", departments);
        return "update-machine.html";
    }

    @PostMapping(value = "/update/{id}")
    public String update(@PathVariable (name = "id") Long id,
            @ModelAttribute(name = "machine") Machine machine) throws MachineNotFoundException {
        LOGGER.info("update()" + machine);
        Machine savedMachine = machineService.updateMachine(machine, id);
        LOGGER.info("update(...)" + savedMachine);
        return "redirect:/machines";
    }

    @GetMapping(value = "/create")
    public String createView(ModelMap modelMap) {
        LOGGER.info("createView()");
        modelMap.addAttribute("machine", new Machine());
        List<Department> departments = departmentService.findAllDepartments();
        modelMap.addAttribute("departments", departments);
        return "create-machine.html";
    }

    @PostMapping(value = "/create")
    public String create(
            String departmentId,
            @ModelAttribute(name = "Machine") Machine machine) {
        LOGGER.info("create(" + departmentId + ")");
        LOGGER.info("create(" + machine + ")");
        Machine savedMachine = machineService.createMachine(machine);
        LOGGER.info("create(...)" + savedMachine);
        return "redirect:/machines";
    }

    @GetMapping(value = "/read/{id}")
    public String read(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws Exception {
        LOGGER.info("read(" + id + ")");
        Machine machine = machineService.findMachineById(id);
        modelMap.addAttribute("machine", machine);
        return "read-machine.html";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(
            @PathVariable(name = "id") Long id) {
        LOGGER.info("delete()");
        machineService.deleteMachine(id);
        return "redirect:/machines";
    }

    @GetMapping(value = "/shedule/{id}")
    public String sheduleView(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws Exception {
        LOGGER.info("sheduleView()");
        Machine machine = machineService.findMachineById(id);
        modelMap.addAttribute("machine", machine);
        List<Department> departments = departmentService.findAllDepartments();
        modelMap.addAttribute("departments", departments);
        return "shedule-machine.html";
    }




    @GetMapping(value = "/export")
    public void exportMachines(@ModelAttribute (name = "machines") List<Machine> machines,
            HttpServletResponse response, Model model) throws Exception {
        LOGGER.info("export()");

        response.setContentType("application/octet-stream");
        DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateTimeFormat.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=machine" + currentDateTime + ".xlsx";

        response.setHeader(headerKey, headerValue);
        exportService.excelModelGenerator(machines);
        exportService.generateExcelFile(response);
        response.flushBuffer();
        LOGGER.info("export(...)");
    }
}
