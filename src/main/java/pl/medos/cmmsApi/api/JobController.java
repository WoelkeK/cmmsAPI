package pl.medos.cmmsApi.api;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/jobs")
    public List findAllJobs() {
        log.debug("findAllJobs()");
        List jobs = jobService.findAllJobs();
        log.debug("findAllJobs(...)" + jobs);
        return jobs;
    }

    @PostMapping("/create")
    public Job createJob(@RequestBody Job job) throws IOException {
        log.debug("createJob(" + job + ")");
        Job createdJob = jobService.createJob(job);
        log.debug("createJob(...)");
        return createdJob;
    }

    @GetMapping("/jobs/{id}")
    public Job findJobById(@PathVariable(name = "id") Long id) throws JobNotFoundException {
       log.debug("readJob(" + id + ")");
        Job readJob = jobService.findJobById(id);
        log.debug("readJob(...) " + readJob);
        return readJob;
    }

    @PutMapping("/jobs/{id}")
    public Job updateJob(@PathVariable(name = "id") Long id,
                         @RequestBody Job job) throws JobNotFoundException {
        log.debug("updateJob(" + id + ")");
        Job updatedJob = jobService.updateJob(job, id);
        log.debug("updateJob(...) " + updatedJob);
        return updatedJob;
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        log.debug("deleteJob(" + id + ")");
        jobService.deleteJob(id);
        log.debug("deleteJob(...)");
    }
}
