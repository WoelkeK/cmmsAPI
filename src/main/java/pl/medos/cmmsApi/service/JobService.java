package pl.medos.cmmsApi.service;

import org.springframework.data.domain.Page;
import pl.medos.cmmsApi.enums.DateOffset;
import pl.medos.cmmsApi.exception.JobNotFoundException;
import pl.medos.cmmsApi.model.Job;

import java.time.LocalDateTime;
import java.util.List;

public interface JobService {

    List<Job> findAllJobs();
    Job createJob(Job job);
    Job findJobById(Long id) throws JobNotFoundException;
    Job updateJob(Job job, Long id) throws JobNotFoundException;
    void deleteJob(Long id);
    Page<Job> findJobPages(int pageNo, int size, String sortField, String sortDirection);
    Page<Job> findByStatusWithPagination(String query, int pageNo, int size, String sortField, String sortDirection);
    Page<Job> findJobByQuery(int pageNo, int pagesize, String query);
    void deleteAllJobs();
    LocalDateTime calculateFutureDate(LocalDateTime initialDate, DateOffset unit, int amount);
}
