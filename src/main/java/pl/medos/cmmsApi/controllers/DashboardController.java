package pl.medos.cmmsApi.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.enums.DateOffset;
import pl.medos.cmmsApi.enums.Decision;
import pl.medos.cmmsApi.enums.JobStatus;
import pl.medos.cmmsApi.exception.JobNotFoundException;
import pl.medos.cmmsApi.exception.MachineNotFoundException;
import pl.medos.cmmsApi.model.*;
import pl.medos.cmmsApi.service.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Controller
@RequestMapping("/dashboards")
@SessionAttributes(names = {"departments", "employees", "machines", "engineers", "images"})
@Slf4j
public class DashboardController {
    private static final String UPLOAD_DIR = System.getProperty("user.home") + File.separator + "images";
    private static final String DEAFULT_IMAGE_FILENAME = "default.jpg";

    private JobService jobService;
    private EmployeeService employeeService;
    private DepartmentService departmentService;
    private MachineService machineService;
    private EngineerService engineerService;
    private ImageService imageService;

    public DashboardController(JobService jobService, EmployeeService employeeService, DepartmentService departmentService, MachineService machineService, EngineerService engineerService, ImageService imageService) {
        this.jobService = jobService;
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.machineService = machineService;
        this.engineerService = engineerService;
        this.imageService = imageService;
    }

    @GetMapping
    public String listViewAll(Model model) {
        log.debug("listView()");
        List<Job> jobs = jobService.findAllJobs();
        model.addAttribute("jobs", jobs);
        List<Department> departments = departmentService.findAllDepartments();
        model.addAttribute("departments", departments);
        List<Employee> employees = employeeService.finadAllEmployees();
        model.addAttribute("employees", employees);
        List<Machine> machines = machineService.findAllMachines();
        model.addAttribute("machines", machines);
        List<Engineer> engineers = engineerService.finadAllEngineers();
        model.addAttribute("engineers", engineers);
        log.debug("listView(...)" + jobs);
        return "main-dashboard.html";
    }

    @GetMapping("/paged")
    public String listView(Model model) throws IOException {
        log.debug("listView()");
        return findJobsPages(1, "requestDate", "asc", model);
    }

    @GetMapping(value = "/page/{pageNo}")
    public String findJobsPages(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDirection,
                                Model model) {
        log.debug("listView()");
        int size = 5;
        String query = "zgłoszenie";
        Page<Job> jobPages = jobService.findByStatusWithPagination(query, pageNo, size, sortField, sortDirection);
        List<Job> jobs = jobPages.getContent();
        model.addAttribute("jobs", jobs);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", jobPages.getTotalPages());
        model.addAttribute("totalItems", jobPages.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDirection);
        model.addAttribute("reverseSortDir", sortDirection.equals("desc") ? "asc" : "desc");
        List<Department> departments = departmentService.findAllDepartments();
        model.addAttribute("departments", departments);
        List<Employee> employees = employeeService.finadAllEmployees();
        model.addAttribute("employees", employees);
        List<Machine> machines = machineService.findAllMachines();
        model.addAttribute("machines", machines);
        List<Engineer> engineers = engineerService.finadAllEngineers();
        model.addAttribute("engineers", engineers);
        log.debug("listView(...)" + jobs);
        return "dashboard-list.html";
    }

    @GetMapping("/images/{fileName:.+}")
    public ResponseEntity<UrlResource> getImage(@PathVariable String fileName) throws MalformedURLException {
        Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName).normalize();
        UrlResource resource = new UrlResource(filePath.toUri());
        if (!resource.exists()) {
            throw new RuntimeException("Image not found: " + fileName);
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    @GetMapping(value = "/create")
    public String createView(Model model) {
        log.debug("createView()");
        Job job = new Job();
        job.setStatus("zgłoszenie");
        job.setJobStatus(JobStatus.AWARIA);
        job.setSolution(" ");
        model.addAttribute("job", job);
        return "create-dashboard";
    }

    @PostMapping(value = "/create")
    public String create(
            @Valid @ModelAttribute(name = "job") Job job,
            BindingResult result,
            Model model,
            MultipartFile image) throws Exception {
        log.debug("create()");

        if (image != null && !image.isEmpty()) {
            String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            job.setPhotoFileName(fileName);
            Path filePath = Paths.get(UPLOAD_DIR + File.separator + fileName);
            Files.write(filePath, image.getBytes());
        } else if (job.getPhotoFileName() == null || job.getPhotoFileName().isEmpty()) {
            job.setPhotoFileName(DEAFULT_IMAGE_FILENAME);
        }
        log.debug("Machine " + job.getMachine().toString());

        try {
            if (job.getMachine() != null && job.getMachine().getId() != null) {
                Machine machineById = machineService.findMachineById(job.getMachine().getId());
                job.setMachine(machineById);
            } else {
                log.debug("Machine is NULL");

            }
        } catch (MachineNotFoundException e) {
            e.printStackTrace();
        }

        if (result.hasErrors()) {
            log.debug("create: result has erorr()" + result.getFieldError());
            model.addAttribute("job", job);
            return "create-dashboard";
        }
        job.setDecision(Decision.NIE);
        job.setOffset(0);
        job.setDateOffset(DateOffset.DNI);
        job.setRequestDate(LocalDateTime.now());
        model.addAttribute("job", job);
        jobService.createJob(job);
        log.debug("create(...)");
        return "redirect:/dashboards";
    }

    @GetMapping(value = "/update/{id}")
    public String updateView(
            @PathVariable(name = "id") Long id,
            Model model) throws Exception {
        log.debug("updateView()");
        Job job = jobService.findJobById(id);
        model.addAttribute("job", job);
        List<Engineer> engineers = engineerService.finadAllEngineers();
        List<Engineer> selectedEngineers = engineers.stream()
                .filter(Engineer::getIsActive)
                .toList();
        model.addAttribute("engineers", selectedEngineers);
        log.debug("updateView(...)" + job.getStatus());
        return "update-dashboard";
    }

    @GetMapping(value = "/read/{id}")
    public String read(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws Exception {
        log.debug("read(" + id + ")");
        Job job = jobService.findJobById(id);
        modelMap.addAttribute("job", job);
        return "update-dashboard";
    }

    @PostMapping(value = "/update/{id}")
    public String update(@PathVariable(name = "id") Long id,
                         @Valid @ModelAttribute(name = "job") Job job,
                         BindingResult result,
                         Model model) throws JobNotFoundException {
        log.debug("update()" + job.getId());
        if (result.hasErrors()) {
            model.addAttribute("job", job);
            return "update-dashboard";
        }
        if ((job.getJobStartTime() != null) && (job.getJobStopTime() != null)) {
            if (job.getJobStopTime().isAfter(job.getJobStartTime())) {
                job.setStatus("zakończono");
            } else {
                return "update-dashboard";
            }
        } else if ((job.getJobStartTime() != null) && (job.getJobStopTime() == null)) {
            job.setStatus("oczekiwanie");
        } else {
            return "update-dashboard";
        }
        model.addAttribute("job", job);
        jobService.updateJob(job, id);
        log.debug("update(...)");
        return "redirect:/dashboards";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(
            @PathVariable(name = "id") Long id) {
        log.debug("delete()");
        jobService.deleteJob(id);
        return "redirect:/jobs";
    }
}
