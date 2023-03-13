package pl.medos.cmmsApi.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.DepartmentNotFoundException;
import pl.medos.cmmsApi.exception.EmployeeNotFoundException;
import pl.medos.cmmsApi.exception.JobNotFoundException;
import pl.medos.cmmsApi.exception.MachineNotFoundException;
import pl.medos.cmmsApi.model.*;
import pl.medos.cmmsApi.service.*;
import pl.medos.cmmsApi.service.impl.MachineServiceImpl;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/jobs")
@SessionAttributes(names = {"departments", "employees", "machines","costs"})
public class WebJobController {

    private static final Logger LOGGER = Logger.getLogger(WebJobController.class.getName());

    private JobService jobService;
    private EmployeeService employeeService;
    private DepartmentService departmentService;
    private MachineService machineService;
    private CostService costService;

    public WebJobController(JobService jobService, EmployeeService employeeService, DepartmentService departmentService, MachineService machineService, CostService costService) {
        this.jobService = jobService;
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.machineService = machineService;
        this.costService = costService;
    }

    @GetMapping
    public String listView(Model model) {
        LOGGER.info("listView()");
        List<Job> jobs = jobService.findAllJobs();
        model.addAttribute("jobs", jobs);
        List<Department> departments = departmentService.findAllDepartments();
        model.addAttribute("departments", departments);
        List<Employee> employees = employeeService.finadAllEmployees();
        model.addAttribute("employees", employees);
        List<Machine> machines = machineService.findAllMachines();
        model.addAttribute("machines", machines);
        List<Cost> costs = costService.findAllCosts();
        model.addAttribute("costs", costs);
        LOGGER.info("listView(...)" + jobs);
        return "list-job.html";
    }

    @GetMapping(value = "/update/{id}")
    public String updateView(
            @PathVariable(name = "id") Long id,
            Model model) throws Exception {
        LOGGER.info("updateView()");
        Job job = jobService.findJobById(id);
        model.addAttribute("job", job);
        LOGGER.info("updateView(...)" + job.getRequestDate());
        return "update-job.html";
    }

    @PostMapping(value = "/update/{id}")
    public String update(@PathVariable(name = "id") Long id,
                         @Valid @ModelAttribute(name = "job") Job job,
                         BindingResult result,
                         Model model) {
        LOGGER.info("update()" + job.getId());

        if (result.hasErrors()) {
            LOGGER.info("update: result has erorr()" + result.getFieldError());
            model.addAttribute("job", job);
            return "update-job";
        }

        model.addAttribute("job", job);
        jobService.updateJob(job);
        LOGGER.info("update(...)");
        return "redirect:/jobs";
    }

    @GetMapping(value = "/create")
    public String createView(Model model) {
        LOGGER.info("createView()");
        model.addAttribute("job", new Job());
        return "create-job.html";
    }

    @PostMapping(value = "/create")
    public String create(
            @Valid @ModelAttribute(name = "job") Job job,
            BindingResult result,
            Model model) {
        LOGGER.info("create()" + job.getId());

        if (result.hasErrors()) {
            LOGGER.info("create: result has erorr()" + result.getFieldError());
            model.addAttribute("job", job);
            return "create-job";
        }

        model.addAttribute("job", job);
        jobService.createJob(job);
        LOGGER.info("create(...)");
        return "redirect:/jobs";
    }

    @GetMapping(value = "/read/{id}")
    public String read(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws Exception {
        LOGGER.info("read(" + id + ")");
        Job job = jobService.findJobById(id);
        modelMap.addAttribute("job", job);
        return "read-job.html";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(
            @PathVariable(name = "id") Long id) {
        LOGGER.info("delete()");
        jobService.deleteJob(id);
        return "redirect:/jobs";
    }

    @GetMapping("/search/message")
    public String searchJobsByMessage(@RequestParam(value = "jobMessage") String query,
                             Model model) {
        LOGGER.info("search()" +query);
        List<Job> jobs = jobService.findJobsByMessage(query);
        model.addAttribute("jobs", jobs);
        return "list-job";
    }

    @GetMapping("/search/department")
    public String searchJobsByDepartment(@RequestParam(value = "jobDepartment") String deaprtmentName,
                                      Model model) throws DepartmentNotFoundException {
        LOGGER.info("search()" + deaprtmentName);
        Department departmentByName = departmentService.findDepartmentById(Long.parseLong(deaprtmentName));
        List<Job> jobs = jobService.findJobsByDepartment(departmentByName);
        model.addAttribute("jobs", jobs);
        return "list-job";
    }

    @GetMapping("/search/employee")
    public String searchJobsByEmployee(@RequestParam(value = "jobEmployee") String employyeName,
                                         Model model) throws EmployeeNotFoundException {
        LOGGER.info("search()");
        Employee employeeByName = employeeService.findEmployeeById(Long.parseLong(employyeName));
        List<Job> jobs = jobService.findJobsByemployee(employeeByName);
        model.addAttribute("jobs", jobs);
        return "list-job";
    }

    @GetMapping("/search/machine")
    public String searchJobsByMachine(@RequestParam(value = "jobMachine") String machineName,
                                       Model model) throws MachineNotFoundException {
        LOGGER.info("search()");
        Machine machineByName = machineService.findMachineById(Long.parseLong(machineName));
        List<Job> jobs = jobService.findJobsByMachine(machineByName);
        model.addAttribute("jobs", jobs);
        return "list-job";
    }
}
