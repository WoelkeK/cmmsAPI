package pl.medos.cmmsApi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.model.*;
import pl.medos.cmmsApi.service.DepartmentService;
import pl.medos.cmmsApi.service.EmployeeService;
import pl.medos.cmmsApi.service.JobService;
import pl.medos.cmmsApi.service.MachineService;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/jobs")
public class WebJobController {

    private static final Logger LOGGER = Logger.getLogger(WebJobController.class.getName());

    private JobService jobService;
    private EmployeeService employeeService;
    private DepartmentService departmentService;
    private MachineService machineService;

    public WebJobController(JobService jobService, EmployeeService employeeService, DepartmentService departmentService, MachineService machineService) {
        this.jobService = jobService;
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.machineService = machineService;
    }

    @GetMapping
    public String listView(ModelMap modelMap) {
        LOGGER.info("listView()");
        List<Job> jobs = jobService.list();
        modelMap.addAttribute("jobs", jobs);
        LOGGER.info("listView(...)" + jobs);
        return "list-job.html";
    }

    @GetMapping(value = "/update/{id}")
    public String updateView(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws Exception {
        LOGGER.info("updateView()");
        Job job = jobService.read(id);
        modelMap.addAttribute("job", job);
        return "update-jobTW.html";
    }

    @PostMapping(value = "/update/")
    public String update(
            @ModelAttribute(name = "job") Job job) {
        LOGGER.info("update()");
        jobService.update(job);
        LOGGER.info("update(...)");
        return "redirect:/jobs";
    }

    @GetMapping(value = "/create")
    public String createView(ModelMap modelMap) {
        LOGGER.info("createView()");
        modelMap.addAttribute("job", new Job());
        List<Department> departments = departmentService.list();
        modelMap.addAttribute("departments", departments);
        List<Employee> employees = employeeService.list();
        modelMap.addAttribute("employees", employees);
        List<Machine> machines = machineService.list();
        modelMap.addAttribute("machines", machines);
        return "create-job.html";
    }

    @PostMapping(value = "/create")
    public String create(
            String employeeId, String jobStartTime, String jobStopTime,
            @ModelAttribute(name = "job") Job job) {
        LOGGER.info("create(" + employeeId + ")");
        LOGGER.info("create(" + jobStartTime+ ")");
        LOGGER.info("create(" + jobStopTime + ")");
        LOGGER.info("create(" + job + ")");
        Job savedJobModel = jobService.create(job);
        LOGGER.info("create(...)" + savedJobModel);
        return "redirect:/";
    }

    @GetMapping(value = "/read/{id}")
    public String read(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws Exception {
        LOGGER.info("read(" + id + ")");
        Job job = jobService.read(id);
        modelMap.addAttribute("job", job);
        return "read-job.html";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(
            @PathVariable(name = "id") Long id) {
        LOGGER.info("delete()");
        jobService.delete(id);
        return "redirect:/jobs";
    }

}
