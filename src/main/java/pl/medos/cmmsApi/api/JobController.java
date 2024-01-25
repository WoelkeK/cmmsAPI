package pl.medos.cmmsApi.api;

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

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/jobs")
@AllArgsConstructor
public class JobController {

    private static final Logger LOGGER = Logger.getLogger(JobController.class.getName());

    private final JobService jobService;

    @GetMapping("/jobs")
    public List findAllJobs() {
        LOGGER.info("findAllJobs()");
        List jobs = jobService.findAllJobs();
        LOGGER.info("findAllJobs(...)" + jobs);
        return jobs;
    }

    @PostMapping("/create")
    public Job createJob(@RequestBody Job job) throws IOException {
        LOGGER.info("createJob(" + job + ")");
        Job createdJob = jobService.createJob(job);
        LOGGER.info("createJob(...)");
        return createdJob;
    }

    @GetMapping("/jobs/{id}")
    public Job findJobById(@PathVariable(name = "id") Long id) throws JobNotFoundException {
        LOGGER.info("readJob(" + id + ")");
        Job readJob = jobService.findJobById(id);
        LOGGER.info("readJob(...) " + readJob);
        return readJob;
    }

    @PutMapping("/jobs/{id}")
    public Job updateJob(@PathVariable(name = "id") Long id,
                         @RequestBody Job job) throws JobNotFoundException {
        LOGGER.info("updateJob(" + id + ")");
        Job updatedJob = jobService.updateJob(job, id);
        LOGGER.info("updateJob(...) " + updatedJob);
        return updatedJob;
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        LOGGER.info("deleteJob(" + id + ")");
        jobService.deleteJob(id);
        LOGGER.info("deleteJob(...)");
    }
}
