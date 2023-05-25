package pl.medos.cmmsApi.controllers;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.imgscalr.Scalr;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.exception.*;
import pl.medos.cmmsApi.model.*;
import pl.medos.cmmsApi.service.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/dashboards")
@SessionAttributes(names = {"departments", "employees", "machines", "engineers", "images"})
public class DashboardController {

    private static final Logger LOGGER = Logger.getLogger(DashboardController.class.getName());

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

    @GetMapping(value = "/list")
    public String listViewAll(Model model) {
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
        Map<Long, String> jobBase64Images = new HashMap<>();
        for (Job job : jobs) {
            jobBase64Images.put(job.getId(), Base64.getEncoder().encodeToString(job.getResizedImage()));
        }
        model.addAttribute("images", jobBase64Images);
        LOGGER.info("listView(...)" + jobs);
        return "dashboard-list.html";
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
        String query ="Zgłoszono";
        Page<Job> jobPages = jobService.findByStatusWithPagination(query,pageNo, size, sortField, sortDirection);
        List<Job> jobs =jobPages.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", jobPages.getTotalPages());
        model.addAttribute("totalItems", jobPages.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDirection);
        model.addAttribute("reverseSortDir", sortDirection.equals("desc") ? "asc" : "desc");

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
        return "dashboard-list.html";
    }

    @GetMapping(value = "/create")
    public String createView(Model model) {
        LOGGER.info("createView()");
        Job job = new Job();
        job.setStatus("Zgłoszono");
        job.setSolution(" ");
        model.addAttribute("job", job);
        return "dashboard-create.html";
    }

    @PostMapping(value = "/create")
    public String create(
            @Valid @ModelAttribute(name = "job") Job job,
            BindingResult result,
            Model model,
            MultipartFile image) throws Exception {
        LOGGER.info("create()");

        if (!image.isEmpty()) {
            LOGGER.info("image processing()");
            byte[] orginalImage = imageService.multipartToByteArray(image);
            byte[] resizeImage = imageService.simpleResizeImage(orginalImage, 200);
            byte[] resizeMaxImage = imageService.simpleResizeImage(orginalImage, 1000);
            job.setResizedImage(resizeImage);
            job.setOriginalImage(resizeMaxImage);
            LOGGER.info("image processing(...)");
        } else {
            LOGGER.info("default image processing()");
            byte[] bytes = imageService.imageToByteArray();
            job.setResizedImage(bytes);
            job.setOriginalImage(bytes);
        }

            if (result.hasErrors()) {
                LOGGER.info("create: result has erorr()" + result.getFieldError());
                model.addAttribute("job", job);
                return "dashboard-create";
            }
            model.addAttribute("job", job);
            jobService.createJob(job);
            LOGGER.info("create(...)");
            return "redirect:/dashboards";
        }

        @GetMapping(value = "/update/{id}")
        public String updateView (
                @PathVariable(name = "id") Long id,
                Model model) throws Exception {
            LOGGER.info("updateView()");
            Job job = jobService.findJobById(id);

            if (job.getStatus().equals("Zgłoszono")) {

                job.setStatus("Zakończono");
                model.addAttribute("job", job);
                LOGGER.info("updateView(...)" + job.getStatus());
                return "dashboard-edit.html";
            } else {
                return "redirect:/dashboards";
            }
        }

        @GetMapping(value = "/read/{id}")
        public String read (
                @PathVariable(name = "id") Long id,
                ModelMap modelMap) throws Exception {
            LOGGER.info("read(" + id + ")");
            Job job = jobService.findJobById(id);
            modelMap.addAttribute("job", job);
            return "read-job.html";
        }

        @PostMapping(value = "/update/{id}")
        public String update (@PathVariable(name = "id") Long id,
                @Valid @ModelAttribute(name = "job") Job job,
                BindingResult result,
                Model model) throws CostNotFoundException, JobNotFoundException {
            LOGGER.info("update()" + job.getId());
            if (result.hasErrors()) {
                LOGGER.info("update: result has erorr()" + result.getFieldError());
                model.addAttribute("job", job);
                return "dashboard-edit";
            }
            model.addAttribute("job", job);
            jobService.updateJob(job, id);
            LOGGER.info("update(...)");
            return "redirect:/dashboards";
        }

        @GetMapping(value = "/delete/{id}")
        public String delete (
                @PathVariable(name = "id") Long id){
            LOGGER.info("delete()");
            jobService.deleteJob(id);
            return "redirect:/jobs";
        }

        @GetMapping("/search/message")
        public String searchJobsByMessage (@RequestParam(value = "jobMessage") String query,
                Model model){
            LOGGER.info("search()" + query);
            List<Job> jobs = jobService.findJobsByMessage(query);
            model.addAttribute("jobs", jobs);
            return "list-job";
        }
    }
