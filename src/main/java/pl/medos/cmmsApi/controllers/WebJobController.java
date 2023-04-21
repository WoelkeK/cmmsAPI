package pl.medos.cmmsApi.controllers;

import jakarta.validation.Valid;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.CostNotFoundException;
import pl.medos.cmmsApi.exception.DepartmentNotFoundException;
import pl.medos.cmmsApi.exception.EmployeeNotFoundException;
import pl.medos.cmmsApi.exception.JobNotFoundException;
import pl.medos.cmmsApi.exception.MachineNotFoundException;
import pl.medos.cmmsApi.model.*;
import pl.medos.cmmsApi.service.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/jobs")
@SessionAttributes(names = {"departments", "employees", "machines"})
public class WebJobController {

    private static final Logger LOGGER = Logger.getLogger(WebJobController.class.getName());

    private JobService jobService;
    private EmployeeService employeeService;
    private DepartmentService departmentService;
    private MachineService machineService;
    private EngineerService engineerService;
        private ImageService imageService;

    public WebJobController(JobService jobService, EmployeeService employeeService, DepartmentService departmentService, MachineService machineService, EngineerService engineerService, ImageService imageService) {
        this.jobService = jobService;
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.machineService = machineService;
        this.engineerService = engineerService;
        this.imageService = imageService;
    }

    //    @Scheduled(fixedDelay = 100000)
    public void createJob() throws IOException {

        Department department = new Department();
        department.setId(1L);
        department.setName("department");
        department.setLocation("location");

        Machine machine = new Machine();
        machine.setId(1L);
        machine.setName("name");
        machine.setModel("model");
        machine.setManufactured(1234);
        machine.setDepartment(department);

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("fullName");
        employee.setPhone("1234567890");
        employee.setEmail("aaaa@bbbb.cc");
        employee.setDepartment(department);

        Job job = new Job();
        job.setRequestDate(LocalDateTime.now());
        job.setUser(new User());
        job.setEmployee(employee);
        job.setDepartment(department);
        job.setMachine(machine);
        job.setMessage("message");
        job.setDirectContact(true);
        job.setSolution("solution");
        job.setJobStartTime(LocalDateTime.now());
        job.setJobStopTime(LocalDateTime.now());
        job.setCalcCost(123.33);

        jobService.createJob(job);

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
        List<Engineer> engineers = engineerService.finadAllEmployees();
        model.addAttribute("engineers", engineers);
        List<Image> images = imageService.findAllImage();
        model.addAttribute("images", images);
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
                         Model model) throws CostNotFoundException, JobNotFoundException {
        LOGGER.info("update()" + job.getId());

        if (result.hasErrors()) {
            LOGGER.info("update: result has erorr()" + result.getFieldError());
            model.addAttribute("job", job);
            return "update-job";
        }
        model.addAttribute("job", job);
        jobService.updateJob(job, id);
        LOGGER.info("update(...)");
        return "redirect:/jobs";
    }

    @GetMapping(value = "/create")
    public String createView(Model model) {
        LOGGER.info("createView()");
        Job job = new Job();
        job.setStatus("Zgłoszono");
        job.setSolution(" ");
        model.addAttribute("job", job);
        return "create-job.html";
    }

    @PostMapping(value = "/create")
    public String create(
            @Valid @ModelAttribute(name = "job") Job job,
            BindingResult result,
            Model model) throws IOException {
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
        LOGGER.info("search()" + query);
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
