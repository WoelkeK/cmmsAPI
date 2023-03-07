//package pl.medos.cmmsApi.controllers;
//
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import pl.medos.cmmsApi.exception.JobNotFoundException;
//import pl.medos.cmmsApi.model.Job;
//import pl.medos.cmmsApi.service.impl.JobService;
//
//import java.util.List;
//import java.util.logging.Logger;
//
//@RestController
//@RequestMapping("/api")
//public class JobController {
//
//    private static final Logger LOGGER = Logger.getLogger(JobController.class.getName());
//
//    private JobService jobService;
//
//    public JobController(JobService jobService) {
//        this.jobService = jobService;
//    }
//
//    @GetMapping("/job")
//    public List list() {
//        LOGGER.info("jobList()");
//        List jobs = jobService.list();
//        LOGGER.info("jobList(...)" + jobs);
//        return jobs;
//    }
//
//    @PostMapping("/job")
//    public Job create(@RequestBody Job job) {
//        LOGGER.info("createJob(" + job + ")");
//        Job createdJob = jobService.create(job);
//        LOGGER.info("createJob(...)");
//        return createdJob;
//    }
//
//    @GetMapping("/job/{id}")
//    public Job read(@PathVariable(name = "id") Long id) throws JobNotFoundException {
//        LOGGER.info("readJob(" + id + ")");
//        Job readedJob = jobService.read(id);
//        LOGGER.info("readJob(...) " + readedJob);
//        return readedJob;
//    }
//
//    @PutMapping("/job")
//    public Job update(@RequestBody Job job) {
//        LOGGER.info("updateJob(" + job + ")");
//        Job updatedJob = jobService.update(job);
//        LOGGER.info("updateJob(...) " + updatedJob);
//        return updatedJob;
//    }
//
//    @DeleteMapping("/job/{id}")
//    public String delete(@PathVariable(name = "id") Long id) {
//        LOGGER.info("deleteJob(" + id + ")");
//        String deleteMessage = jobService.delete(id);
//        LOGGER.info("deleteJob(...)");
//        return deleteMessage;
//    }
//}
