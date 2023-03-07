package pl.medos.cmmsApi.service;

import pl.medos.cmmsApi.exception.JobNotFoundException;
import pl.medos.cmmsApi.model.Job;

import java.util.List;

public interface JobService {

    List<Job> findAllJobs();

    Job createJob(Job job);

    Job findJobById(Long id) throws JobNotFoundException;

    Job updateJob(Job job);

    void deleteJob(Long id);
}
