package pl.medos.cmmsApi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.model.Job;
import pl.medos.cmmsApi.service.JobService;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/jobs")
public class WebJobController {

    private static final Logger LOGGER = Logger.getLogger(WebJobController.class.getName());

    private JobService jobService;

    public WebJobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public String listView(ModelMap modelMap) {
        LOGGER.info("listView()");
        List<Job> jobs = jobService.list();
        modelMap.addAttribute("jobs", jobs);

        return "list-jobsTW.html";
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
        return "create-jobForm.html";
    }

    @PostMapping(value = "/create")
    public String create(
            @ModelAttribute(name = "job") Job job) {
        LOGGER.info("create(" + job + ")");
        Job savedJobModel = jobService.create(job);
        LOGGER.info("create(...)" + savedJobModel);
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
