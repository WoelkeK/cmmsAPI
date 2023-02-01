package pl.medos.cmmsApi.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.medos.cmmsApi.model.Job;
import pl.medos.cmmsApi.service.JobService;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class JobController {

    private static final Logger LOGGER = Logger.getLogger(JobController.class.getName());

    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/job")
    public List allJobList() {
        LOGGER.info("allJobList()");
        List allJob = jobService.listAll();
        LOGGER.info("allJobList(...)" + allJob);
        return allJob;
    }

    @PostMapping("/job")
    public Job createJob(@RequestBody Job job) {
        LOGGER.info("createJob(" + job + ")");
        Job createdJob = jobService.create(job);
        LOGGER.info("createJob(...)");
        return createdJob;
    }

    @GetMapping("/job/{id}")
    public Job readJob(@PathVariable(name = "id") Long id) {
        LOGGER.info("readJob(" + id + ")");
        Job readedJob = jobService.read(id);
        LOGGER.info("readJob(...) " + readedJob);
        return readedJob;
    }

    @PutMapping("/job")
    public Job updateJob(@RequestBody Job job) {
        LOGGER.info("updateJob(" + job + ")");
        Job updatedJob = jobService.update(job);
        LOGGER.info("updateJob(...) " + updatedJob);
        return updatedJob;
    }

    @DeleteMapping("/job/{id}")
    public String deleteJob(@PathVariable(name = "id") Long id) {
        LOGGER.info("deleteJob(" + id + ")");
        String deleteMessage = jobService.delete(id);
        LOGGER.info("deleteJob(...)");
        return deleteMessage;
    }
}
