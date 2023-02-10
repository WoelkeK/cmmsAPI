package pl.medos.cmmsApi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.model.Job;
import pl.medos.cmmsApi.model.Machine;
import pl.medos.cmmsApi.service.DepartmentService;
import pl.medos.cmmsApi.service.EmployeeService;
import pl.medos.cmmsApi.service.JobService;
import pl.medos.cmmsApi.service.MachineService;

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
        List<Machine> machines = machineService.list();
        modelMap.addAttribute("machines", machines);
        LOGGER.info("listView(...)" + machines);
        return "list-machine.html";
    }

    @GetMapping(value = "/update/{id}")
    public String updateView(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws Exception {
        LOGGER.info("updateView()");
        Machine machine = machineService.read(id);
        modelMap.addAttribute("machine", machine);
        List<Department> departments = departmentService.list();
        modelMap.addAttribute("departments", departments);
        return "update-machine.html";
    }

    @PostMapping(value = "/update")
    public String update(
            @ModelAttribute(name = "machine") Machine machine) {
        LOGGER.info("update()" + machine);
        Machine savedMachine = machineService.update(machine);
        LOGGER.info("update(...)" + savedMachine);
        return "redirect:/machines";
    }

    @GetMapping(value = "/create")
    public String createView(ModelMap modelMap) {
        LOGGER.info("createView()");
        modelMap.addAttribute("machine", new Machine());
        List<Department> departments = departmentService.list();
        modelMap.addAttribute("departments", departments);
        return "create-machine.html";
    }

    @PostMapping(value = "/create")
    public String create(
            String departmentId,
            @ModelAttribute(name = "Machine") Machine machine) {
        LOGGER.info("create(" + departmentId + ")");
        LOGGER.info("create(" + machine + ")");
        Machine savedMachine = machineService.create(machine);
        LOGGER.info("create(...)" + savedMachine);
        return "redirect:/machines";
    }

    @GetMapping(value = "/read/{id}")
    public String read(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws Exception {
        LOGGER.info("read(" + id + ")");
        Machine machine = machineService.read(id);
        modelMap.addAttribute("machine", machine);
        return "read-machine.html";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(
            @PathVariable(name = "id") Long id) {
        LOGGER.info("delete()");
        machineService.delete(id);
        return "redirect:/machines";
    }
}
