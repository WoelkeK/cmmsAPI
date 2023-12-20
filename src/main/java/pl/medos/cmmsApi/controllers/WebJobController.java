package pl.medos.cmmsApi.controllers;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.exception.CostNotFoundException;
import pl.medos.cmmsApi.exception.JobNotFoundException;
import pl.medos.cmmsApi.model.*;
import pl.medos.cmmsApi.service.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
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
    private ImportService importService;
    private ExportService exportService;

    public WebJobController(JobService jobService, EmployeeService employeeService, DepartmentService departmentService, MachineService machineService, EngineerService engineerService, ImageService imageService, ImportService importService, ExportService exportService) {
        this.jobService = jobService;
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.machineService = machineService;
        this.engineerService = engineerService;
        this.imageService = imageService;
        this.importService = importService;
        this.exportService = exportService;
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
        return "list-job";
    }

    @GetMapping
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
//        List<Engineer> engineers = engineerService.finadAllEmployees();
//        model.addAttribute("engineers", engineers);

        Map<Long, String> jobBase64Images = new HashMap<>();
        for (Job job : jobs) {
            jobBase64Images.put(job.getId(), Base64.getEncoder().encodeToString(job.getResizedImage()));
        }
        model.addAttribute("images", jobBase64Images);
        LOGGER.info("listView(...)" + jobs);
        return "main-job";
    }

    @GetMapping(value = "/update/{id}")
    public String updateView(
            @PathVariable(name = "id") Long id,
            Model model) throws Exception {
        LOGGER.info("updateView()");
        Job job = jobService.findJobById(id);
        LOGGER.info(job.getEmployee().getId().toString());
        model.addAttribute("job", job);
        LOGGER.info("updateView(...)" + job.getRequestDate());
        return "update-job";
    }

    @PostMapping(value = "/update/{id}")
    public String update(@PathVariable(name = "id") Long id,
                         @Valid @ModelAttribute(name = "job") Job job,
                         BindingResult result,
                         Model model,
                         MultipartFile image) throws JobNotFoundException, IOException {
        LOGGER.info("update()" + job.getId());

        if (image.isEmpty() || image.getBytes() == null) {
            LOGGER.info("multipart file not present");

            if (image.getSize() == 0 && job.getOriginalImage() == null) {
                LOGGER.info("default image");
                byte[] bytes = imageService.imageToByteArray();
                job.setResizedImage(bytes);
                job.setOriginalImage(bytes);
            }
        } else {
            LOGGER.info("procesed Image() ");
            byte[] orginalImage = imageService.multipartToByteArray(image);
            byte[] resizeImage = imageService.simpleResizeImage(orginalImage, 300);
            byte[] resizeMaxImage = imageService.simpleResizeImage(orginalImage, 800);
            job.setOriginalImage(orginalImage);
            job.setResizedImage(resizeMaxImage);
        }
        LOGGER.info("image prepared");

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
        return "create-job";
    }

    @PostMapping(value = "/create")
    public String create(
            @Valid @ModelAttribute(name = "job") Job job,
            BindingResult result,
            Model model,
            MultipartFile image) throws Exception {
        LOGGER.info("create()" + job.getId());

        if (image.getSize() == 0 && job.getOriginalImage() == null) {
            LOGGER.info("default image");
            byte[] bytes = imageService.imageToByteArray();
            job.setResizedImage(bytes);
            job.setOriginalImage(bytes);
        } else {
            LOGGER.info("multipart file present");
            if (image.isEmpty() || image.getBytes() == null) {
                return "create-job";
            } else {
                LOGGER.info("procesed Image() ");
                byte[] orginalImage = imageService.multipartToByteArray(image);
                byte[] resizeImage = imageService.simpleResizeImage(orginalImage, 300);
                byte[] resizeMaxImage = imageService.simpleResizeImage(orginalImage, 800);
                job.setOriginalImage(orginalImage);
                job.setResizedImage(resizeMaxImage);
            }
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
        return "read-job";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(
            @PathVariable(name = "id") Long id) {
        LOGGER.info("delete()");
        jobService.deleteJob(id);
        return "redirect:/jobs";
    }

    @GetMapping(value = "/deleteAll")
    public String deleteAll(){
        LOGGER.info("delete()");
        jobService.deleteAllJobs();
        return "redirect:/jobs";
    }


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

    @GetMapping("/search/query")
    public String searchJobByName(
            @RequestParam(value = "query") String query,
            @RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
            Model model) {
        LOGGER.info("search()");
        int pagesize = 10;
        Page<Job> jobByQuery = jobService.findJobByQuery(pageNo, pagesize, query);
        LOGGER.info("findJobByQuery()");
        model.addAttribute("jobs", jobByQuery);
        model.addAttribute("currentPage", pageNo);
        return "main-job";
    }


    @GetMapping(value = "/file")
    public String showUploadForm() {
        return "uploadJob-form";
    }

    @PostMapping(value = "/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {

        LOGGER.info("importJobs()");
        if (file.isEmpty()) {
            LOGGER.info("Proszę wybrać plik do importu");
            return "redirect/jobs";
        }

        List<Job> jobs = importService.importExcelJobData(file);

        jobs.forEach((job) -> {
           jobService.createJob(job);
        });
        LOGGER.info("importJobs(...) ");
        return "redirect:/jobs";
    }

    @GetMapping(value = "/export")
    public void exportJobs(@ModelAttribute(name = "jobs") List<Job> jobs,
                                HttpServletResponse response, Model model) throws Exception {
        LOGGER.info("export()");
        response.setContentType("application/octet-stream");
        DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateTimeFormat.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=hardware" + currentDateTime + ".xlsx";

        response.setHeader(headerKey, headerValue);
        exportService.excelJobsModelGenerator(jobs);
        exportService.generateExcelJobFile(response);
        response.flushBuffer();
        LOGGER.info("export(...)");
    }

    // TODO: 20.12.2023 dokończyć implementacje exportu awarii do pliku excel
}
