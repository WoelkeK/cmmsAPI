package pl.medos.cmmsApi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.medos.cmmsApi.exception.CostNotFoundException;
import pl.medos.cmmsApi.exception.JobNotFoundException;
import pl.medos.cmmsApi.exception.MachineNotFoundException;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.model.Job;
import pl.medos.cmmsApi.model.Machine;

import java.io.IOException;
import java.util.List;

public interface JobService {

    List<Job> findAllJobs();

    Job createJob(Job job) throws IOException;

    Job findJobById(Long id) throws JobNotFoundException;

    Job updateJob(Job job, Long id) throws JobNotFoundException;

    void deleteJob(Long id);

    Page<Job> findJobPages(int pageNo, int size, String sortField, String sortDirection);
    Page<Job> findByStatusWithPagination(String query, int pageNo, int size, String sortField, String sortDirection);
    Page<Job> findJobByQuery(int pageNo, int pagesize, String query);
}
