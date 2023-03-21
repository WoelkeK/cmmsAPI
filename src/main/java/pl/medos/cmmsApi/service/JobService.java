package pl.medos.cmmsApi.service;

import pl.medos.cmmsApi.exception.CostNotFoundException;
import pl.medos.cmmsApi.exception.JobNotFoundException;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.model.Job;
import pl.medos.cmmsApi.model.Machine;

import java.util.List;

public interface JobService {

    List<Job> findAllJobs();

    List<Job> findJobsByMessage(String query);

    List<Job> findJobsByDepartment(Department department);

    List<Job> findJobsByemployee(Employee employeeByName);

    Job createJob(Job job);

    Job findJobById(Long id) throws JobNotFoundException;

    Job updateJob(Job job, Long id) throws JobNotFoundException;

    void deleteJob(Long id);

    List<Job> findJobsByMachine(Machine machineByName);
}
