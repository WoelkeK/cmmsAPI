package pl.medos.cmmsApi.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.medos.cmmsApi.exception.JobNotFoundException;
import pl.medos.cmmsApi.model.Job;
import pl.medos.cmmsApi.service.JobService;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class JobController {

    private static final Logger LOGGER = Logger.getLogger(JobController.class.getName());

    private final JobService jobService;

    @GetMapping("/job")
    public List list() {
        LOGGER.info("jobList()");
        List jobs = jobService.findAllJobs();
        LOGGER.info("jobList(...)" + jobs);
        return jobs;
    }

    @PostMapping("/job")
    public Job create(@RequestBody Job job) {
        LOGGER.info("createJob(" + job + ")");
        Job createdJob = jobService.createJob(job);
        LOGGER.info("createJob(...)");
        return createdJob;
    }

    @GetMapping("/job/{id}")
    public Job read(@PathVariable(name = "id") Long id) throws JobNotFoundException {
        LOGGER.info("readJob(" + id + ")");
        Job readedJob = jobService.findJobById(id);
        LOGGER.info("readJob(...) " + readedJob);
        return readedJob;
    }

    @PutMapping("/job")
    public Job update(@PathVariable(name = "id") Long id,
                      @RequestBody Job job) {
        LOGGER.info("updateJob(" + job + ")");
        Job updatedJob = jobService.updateJob(job);
        LOGGER.info("updateJob(...) " + updatedJob);
        return updatedJob;
    }

    @DeleteMapping("/job/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        LOGGER.info("deleteJob(" + id + ")");
        jobService.deleteJob(id);
        LOGGER.info("deleteJob(...)");
    }
}
