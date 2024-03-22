package pl.medos.cmmsApi.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.exception.JobNotFoundException;
import pl.medos.cmmsApi.model.*;
import pl.medos.cmmsApi.service.*;
import pl.medos.cmmsApi.util.imports.ImportJob;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

@Controller
@RequestMapping("/jobs")
@SessionAttributes(names = {"departments", "employees", "machines", "engineers", "images"})
@Slf4j
public class WebJobController {

    private static final String UPLOAD_DIR = System.getProperty("user.home") + File.separator + "images";
    private static final String DEAFULT_IMAGE_FILENAME = "default.jpg";
    private List<Job> actualJobs = new ArrayList<>();
    private JobService jobService;
    private EmployeeService employeeService;
    private DepartmentService departmentService;
    private MachineService machineService;
    private EngineerService engineerService;
    private ImageService imageService;
    private ExportService exportService;
    private ImportJob importJob;

    public WebJobController(JobService jobService, EmployeeService employeeService, DepartmentService departmentService, MachineService machineService, ImageService imageService, ExportService exportService, ImportJob importJob) {
        this.jobService = jobService;
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.machineService = machineService;
        this.imageService = imageService;
        this.exportService = exportService;
        this.importJob = importJob;
    }

    @GetMapping(value = "/list")
    public String listViewAll(Model model) {
        log.debug("listViewAll()");
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
        log.debug("listViewAll(...)" + jobs);
        return "list-job";
    }

    @GetMapping
    public String listView(
            @RequestParam(name = "pageNo", defaultValue = "1", required = false) String page,
            Model model) throws IOException {
        log.debug("listView()");
        int pageNo = Integer.parseInt(page);
        return findJobsPages(pageNo, "requestDate", "asc", model);
    }

    @GetMapping(value = "/page/{pageNo}")
    public String findJobsPages(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDirection,
                                Model model) {
        log.debug("listView()");
        int size = 10;
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
        log.debug("listView(...)" + jobs);
        return "main-job";
    }

    @GetMapping(value = "/update/{id}")
    public String updateView(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "pageNo", required = false, defaultValue = "1") int pageNo,
            Model model) throws Exception {
        log.debug("updateView()");
        Job job = jobService.findJobById(id);
        model.addAttribute("job", job);
        model.addAttribute("pageNo", pageNo);
        log.debug("updateView(...)" + job.getRequestDate());
        return "update-job";
    }

    @PostMapping(value = "/update/{id}")
    public String update(@PathVariable(name = "id") Long id,
                         @RequestParam(name = "pageNo") String page,
                         @Valid @ModelAttribute(name = "job") Job job,
                         BindingResult result,
                         Model model,
                         MultipartFile image) throws JobNotFoundException, IOException {
        log.debug("update()" + job.getId() + " " + page);
        int pageNo = Integer.parseInt(page);
        if (image != null && !image.isEmpty()) {
            String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            job.setPhotoFileName(fileName);
            Path filePath = Paths.get(UPLOAD_DIR + File.separator + fileName);
            log.debug("zapis: " + fileName);
            Files.write(filePath, image.getBytes());
        } else if (job.getPhotoFileName() == null || job.getPhotoFileName().isEmpty()) {
            job.setPhotoFileName(DEAFULT_IMAGE_FILENAME);
        }
        log.debug("image prepared");
        if (result.hasErrors()) {
            log.debug("update: result has erorr()" + result.getFieldError());
            model.addAttribute("job", job);
            return "update-job";
        }
        if (job.getJobStartTime() == null) {
            log.debug("Nieprawidłowo wybrany początkowy czas pracy");
            return "update-job";
        } else if ((job.getJobStartTime() != null) && (job.getJobStopTime() == null)) {
            job.setStatus("oczekiwanie");
        } else {
            if (job.getJobStopTime().isAfter(job.getJobStartTime())) {
                job.setStatus("zakończono");
            } else {
                log.debug("Nieprawidłowo wypełniony czas pracy");
                return "update-job";
            }
        }
        model.addAttribute("job", job);
        jobService.updateJob(job, id);
        log.debug("update(...)");
        return "redirect:/jobs?pageNo=" + pageNo;
    }

    @GetMapping(value = "/create")
    public String createView(Model model) {
        log.debug("createView()");
        Job job = new Job();
        job.setStatus("zgłoszenie");
        job.setSolution(" ");
        model.addAttribute("job", job);
        return "create-job";
    }

    @PostMapping(value = "/create")
    public String create(
            @Valid @ModelAttribute(name = "job") Job job,
            BindingResult result,
            Model model,
            MultipartFile image) throws Exception {
        log.debug("create()" + job.getId());
        if (image != null && !image.isEmpty()) {
            String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            job.setPhotoFileName(fileName);
            Path filePath = Paths.get(UPLOAD_DIR + File.separator + fileName);
            log.debug("zapis: " + filePath);
            Files.write(filePath, image.getBytes());
        } else if (job.getPhotoFileName() == null || job.getPhotoFileName().isEmpty()) {
            job.setPhotoFileName(DEAFULT_IMAGE_FILENAME);
        }
        if (result.hasErrors()) {
            log.debug("create: result has erorr()" + result.getFieldError());
            model.addAttribute("job", job);
            return "create-job";
        }
        model.addAttribute("job", job);
        jobService.createJob(job);
        log.debug("create(...)");
        return "redirect:/jobs";
    }

    @GetMapping(value = "/read/{id}")
    public String read(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws Exception {
        log.debug("read(" + id + ")");
        Job job = jobService.findJobById(id);
        modelMap.addAttribute("job", job);
        return "read-job";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(
            @RequestParam(name = "pageNo") int pageNo,
            @PathVariable(name = "id") Long id) {
        log.debug("delete()");
        jobService.deleteJob(id);
        return "redirect:/jobs?pageNo=" + pageNo;
    }

    @GetMapping(value = "/deleteAll")
    public String deleteAll() {
        log.debug("delete()");
        jobService.deleteAllJobs();
        return "redirect:/jobs";
    }

    @GetMapping("/search/query")
    public String searchJobByName(@RequestParam(name = "query") String query,
                                  @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                                  Model model) {

        log.debug("search() " + pageNo);
        List<Job> jobByQuery = jobService.getJobList(query);

        model.addAttribute("jobs", jobByQuery);
        List<Department> departments = departmentService.findAllDepartments();
        model.addAttribute("departments", departments);
        List<Employee> employees = employeeService.finadAllEmployees();
        model.addAttribute("employees", employees);
        List<Machine> machines = machineService.findAllMachines();
        model.addAttribute("machines", machines);
        model.addAttribute("currentPage", pageNo);
        log.debug("listView(...)");
        return "main-job";
    }

    @GetMapping(value = "/file")
    public String showUploadForm() {
        return "uploadJob-form";
    }

    @PostMapping(value = "/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        log.debug("importJobs()");
        if (file.isEmpty()) {
            log.debug("Proszę wybrać plik do importu");
            return "redirect/jobs";
        }
        List<Job> jobs = importJob.importExcelJobData(file);
        jobs.forEach((job) -> {
            jobService.createJob(job);
        });
        log.debug("importJobs(...) ");
        return "redirect:/jobs";
    }

    @GetMapping(value = "/exportAll")
    public void exportJobsAll(HttpServletResponse response, Model model) throws Exception {
        log.debug("export()");
        List<Job> jobs = jobService.findAllJobs();
        response.setContentType("application/octet-stream");
        DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateTimeFormat.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=awarie" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        exportService.excelJobsModelGenerator(jobs);
        exportService.generateExcelJobFile(response);
        response.flushBuffer();
        log.debug("export(...)");
    }

    @GetMapping(value = "/export")
    public void exportJobs(HttpServletResponse response) throws Exception {
        log.debug("export()");
        response.setContentType("application/octet-stream");
        DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateTimeFormat.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=awarie" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        exportService.excelJobsModelGenerator(actualJobs);
        exportService.generateExcelJobFile(response);
        response.flushBuffer();
        log.debug("export(...)");
    }
}