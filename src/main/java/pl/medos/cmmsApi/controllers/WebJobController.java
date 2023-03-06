package pl.medos.cmmsApi.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
@SessionAttributes(names = {"departments"})
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
            Model model) throws Exception {
        LOGGER.info("updateView()");
        List<Department> departments = departmentService.list();
        model.addAttribute("departments", departments);
        List<Employee> employees = employeeService.list();
        model.addAttribute("employees", employees);
        List<Machine> machines = machineService.list();
        model.addAttribute("machines", machines);
        Job job = jobService.read(id);
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
//            List<Department> departments = departmentService.list();
//            model.addAttribute("departments", departments);
            List<Employee> employees = employeeService.list();
            model.addAttribute("employees", employees);
            List<Machine> machines = machineService.list();
            model.addAttribute("machines", machines);
            return "update-job";
        }

        model.addAttribute("job", job);

        jobService.update(job);
        LOGGER.info("update(...)");
        return "redirect:/jobs";
    }

    @GetMapping(value = "/create")
    public String createView(Model model) {
        LOGGER.info("createView()");
        model.addAttribute("job", new Job());
        List<Department> departments = departmentService.list();
        model.addAttribute("departments", departments);
        List<Employee> employees = employeeService.list();
        model.addAttribute("employees", employees);
        List<Machine> machines = machineService.list();
        model.addAttribute("machines", machines);
        return "create-job.html";
    }

    @PostMapping(value = "/create")
    public String create(
                         @Valid @ModelAttribute(name = "job") Job job,
                         BindingResult result,
                         Model model) {
        LOGGER.info("create()" + job.getId());

        if (result.hasErrors()) {
            LOGGER.info("update: result has erorr()" + result.getFieldError());
            model.addAttribute("job", job);
//            List<Department> departments = departmentService.list();
//            model.addAttribute("departments", departments);
            List<Employee> employees = employeeService.list();
            model.addAttribute("employees", employees);
            List<Machine> machines = machineService.list();
            model.addAttribute("machines", machines);
            return "create-job";
        }

        model.addAttribute("job", job);
        jobService.create(job);
        LOGGER.info("create(...)");
        return "redirect:/jobs";
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
