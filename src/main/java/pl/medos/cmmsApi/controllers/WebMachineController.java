package pl.medos.cmmsApi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.DepartmentNotFoundException;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Job;
import pl.medos.cmmsApi.model.Machine;
import pl.medos.cmmsApi.service.DepartmentService;
import pl.medos.cmmsApi.service.MachineService;
import pl.medos.cmmsApi.service.impl.MachineServiceImpl;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/machines")
public class WebMachineController {

    private static final Logger LOGGER = Logger.getLogger(WebMachineController.class.getName());

    private MachineService machineService;
    private DepartmentService departmentService;

    public WebMachineController(MachineService machineService, DepartmentService departmentService) {
        this.machineService = machineService;
        this.departmentService = departmentService;
    }

    @GetMapping
    public String listView(ModelMap modelMap) {
        LOGGER.info("listView()");
        List<Machine> machines = machineService.findAllMachines();
        modelMap.addAttribute("machines", machines);
        LOGGER.info("listView(...)" + machines);
        return "list-machine.html";
    }

    @GetMapping("/search/name")
    public String searchMachineByName(@RequestParam(value = "machineName") String query,
                             Model model) {
        LOGGER.info("search()");
        List<Machine> machines = machineService.findMachinesByName(query);
        model.addAttribute("machines", machines);
        return "list-machine";
    }

    @GetMapping("/search/department")
    public String searchJMachineByDepartment(@RequestParam(value = "machineDepartment") String departmentName,
                             Model model) throws DepartmentNotFoundException {
        LOGGER.info("search()");
        Department departmentByName = departmentService.findDepartmentByName(departmentName);
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

    @PostMapping(value = "/update")
    public String update(
            @ModelAttribute(name = "machine") Machine machine) {
        LOGGER.info("update()" + machine);
        Machine savedMachine = machineService.updateMachine(machine);
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
}
