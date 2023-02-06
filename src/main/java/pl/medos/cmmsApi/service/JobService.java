//package pl.medos.cmmsApi.service;
//
//import org.springframework.stereotype.Service;
//import pl.medos.cmmsApi.model.Job;
//import pl.medos.cmmsApi.repository.JobRepository;
//
//import java.util.List;
//import java.util.logging.Logger;
//
//@Service
//public class JobService {
//
//    private static final Logger LOGGER = Logger.getLogger(JobService.class.getName());
//
//    private JobRepository jobRepository;
//
//    public JobService(JobRepository jobRepository) {
//        this.jobRepository = jobRepository;
//    }
//
//    public List list() {
//        LOGGER.info("list()");
//        List<Job> jobList = jobRepository.findAll();
//        LOGGER.info("List(...)");
//        return jobList;
//
//    }
//
//    public Job create(Job job) {
//        LOGGER.info("create(" + job + ")");
//       Job createdJob = jobRepository.save(job);
//        LOGGER.info("create(...)");
//        return createdJob;
//
//    }
//
//    public Job read(Long id) {
//        LOGGER.info("read()");
//        Job readedJob = jobRepository.findById(id).orElseThrow();
//        LOGGER.info("read(...)");
//        return readedJob;
//
//    }
//
//    public Job update(Job job) {
//        LOGGER.info("update()");
//        Job editedJob= jobRepository.findById(job.getId()).orElseThrow();
//        editedJob.setRequestTime(job.getRequestTime());
//        editedJob.setUser(job.getUser());
//        editedJob.setDepartment(job.getDepartment());
//        editedJob.setMachine(job.getMachine());
//        editedJob.setMessage(job.getMessage());
//        editedJob.setDirectContact(job.getDirectContact());
//        editedJob.setSolution(job.getSolution());
//        editedJob.setJobStartTime(job.getJobStartTime());
//        editedJob.setJobStopTime(job.getJobStopTime());
//
//        Job updatedJob = jobRepository.save(editedJob);
//        LOGGER.info("update(...) " + updatedJob);
//        return updatedJob;
//    }
//
//    public String delete(Long id) {
//        LOGGER.info("delete()");
//        jobRepository.deleteById(id);
//        LOGGER.info("delete(...)");
//        return "Record " + id + " deleted!";
//    }
//}
