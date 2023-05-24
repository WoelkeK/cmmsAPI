package pl.medos.cmmsApi.controllers;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.imgscalr.Scalr;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.exception.CostNotFoundException;
import pl.medos.cmmsApi.exception.DepartmentNotFoundException;
import pl.medos.cmmsApi.exception.EmployeeNotFoundException;
import pl.medos.cmmsApi.exception.ImageNotFoundException;
import pl.medos.cmmsApi.exception.JobNotFoundException;
import pl.medos.cmmsApi.exception.MachineNotFoundException;
import pl.medos.cmmsApi.model.*;
import pl.medos.cmmsApi.service.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping("/jobs")
@SessionAttributes(names = {"departments", "employees", "machines", "engineers", "images"})
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

    @GetMapping(value = "/list")
    public String listViewAll(Model model) {
        LOGGER.info("listViewAll()");
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

        Map<Long, String> jobBase64Images = new HashMap<>();
        for (Job job : jobs) {
            jobBase64Images.put(job.getId(), Base64.getEncoder().encodeToString(job.getResizedImage()));
        }
        model.addAttribute("images", jobBase64Images);
        LOGGER.info("listViewAll(...)" + jobs);
        return "list-job.html";
    }

    @GetMapping("")
    public String listView(Model model) throws IOException {
        LOGGER.info("listView()");
        return findJobsPages(1, "requestDate", "asc", model);
    }

    @GetMapping(value = "/page/{pageNo}")
    public String findJobsPages(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDirection,
                                Model model) {
        LOGGER.info("listView()");
        int size = 5;
        Page<Job> jobPages = jobService.findJobPages(pageNo, size, sortField, sortDirection);
        List<Job> jobs = jobPages.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", jobPages.getTotalPages());
        model.addAttribute("totalItems", jobPages.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDirection);
        model.addAttribute("reverseSortDir", sortDirection.equals("asc") ? "desc" : "asc");

        model.addAttribute("jobs", jobs);

        List<Department> departments = departmentService.findAllDepartments();
        model.addAttribute("departments", departments);
        List<Employee> employees = employeeService.finadAllEmployees();
        model.addAttribute("employees", employees);
        List<Machine> machines = machineService.findAllMachines();
        model.addAttribute("machines", machines);
        List<Engineer> engineers = engineerService.finadAllEmployees();
        model.addAttribute("engineers", engineers);

        Map<Long, String> jobBase64Images = new HashMap<>();
        for (Job job : jobs) {
            jobBase64Images.put(job.getId(), Base64.getEncoder().encodeToString(job.getResizedImage()));
        }
        model.addAttribute("images", jobBase64Images);
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
            Model model,
            MultipartFile image) throws Exception {
        LOGGER.info("create()" + job.getId());
        if (image != null) {

            byte[] orginalImage = imageService.multipartToByteArray(image);
            byte[] resizeImage = imageService.simpleResizeImage(orginalImage, 200);
            byte[] resizeMaxImage = imageService.simpleResizeImage(orginalImage, 1000);
            job.setResizedImage(resizeImage);
            job.setOriginalImage(resizeMaxImage);
        } else {
            byte[] bytes = imageService.imageToByteArray();
            job.setResizedImage(bytes);
            job.setOriginalImage(bytes);
        }
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

    @GetMapping(value = "/search/message")
    public String searchJobsByMessage(@RequestParam(value = "jobMessage") String query,
                                      Model model) {
        LOGGER.info("search()" + query);
        List<Job> jobs = jobService.findJobsByMessage(query);
        model.addAttribute("jobs", jobs);
        return "list-job";
    }

//    @GetMapping(value = "/search/department")
//    public String searchJobsByDepartment(@RequestParam(value = "jobDepartment") String deaprtmentName,
//                                         Model model) throws DepartmentNotFoundException {
//        LOGGER.info("search()" + deaprtmentName);
//        Department departmentByName = departmentService.findDepartmentById(Long.parseLong(deaprtmentName));
//        List<Job> jobs = jobService.findJobsByDepartment(departmentByName);
//        model.addAttribute("jobs", jobs);
//        return "list-job";
//    }

//    @GetMapping(value = "/search/employee")
//    public String searchJobsByEmployee(@RequestParam(value = "jobEmployee") String employyeName,
//                                       Model model) throws EmployeeNotFoundException {
//        LOGGER.info("search()");
//        Employee employeeByName = employeeService.findEmployeeById(Long.parseLong(employyeName));
//        List<Job> jobs = jobService.findJobsByemployee(employeeByName);
//        model.addAttribute("jobs", jobs);
//        return "list-job";
//    }

//    @GetMapping(value = "/search/machine")
//    public String searchJobsByMachine(@RequestParam(value = "jobMachine") String machineName,
//                                      Model model) throws MachineNotFoundException {
//        LOGGER.info("search()");
//        Machine machineByName = machineService.findMachineById(Long.parseLong(machineName));
//        List<Job> jobs = jobService.findJobsByMachine(machineByName);
//        model.addAttribute("jobs", jobs);
//        return "list-job";
//    }

    @GetMapping(value = "/downloadfile")
    public void downloadFile(@Param("id") Long id, Model model, HttpServletResponse response) throws IOException, JobNotFoundException {
        Job jobById = jobService.findJobById(id);
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename = " + jobById.getRequestDate() + ".jpg";
        response.setHeader(headerKey, headerValue);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(jobById.getOriginalImage());
        outputStream.close();
    }
}
