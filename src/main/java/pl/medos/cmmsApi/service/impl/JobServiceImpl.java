package pl.medos.cmmsApi.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.enums.DateOffset;
import pl.medos.cmmsApi.enums.Decision;
import pl.medos.cmmsApi.enums.JobStatus;
import pl.medos.cmmsApi.exception.JobNotFoundException;
import pl.medos.cmmsApi.model.*;
import pl.medos.cmmsApi.repository.JobRepository;
import pl.medos.cmmsApi.repository.entity.JobEntity;
import pl.medos.cmmsApi.service.ImageService;
import pl.medos.cmmsApi.service.JobService;
import pl.medos.cmmsApi.service.mapper.JobMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@Slf4j
public class JobServiceImpl implements JobService {

    private JobRepository jobRepository;
    private JobMapper jobMapper;
    private ImageService imageService;

    public JobServiceImpl(JobRepository jobRepository, JobMapper jobMapper, ImageService imageService) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
        this.imageService = imageService;
    }

    @Override
    public List<Job> findAllJobs() {
        log.debug("list()");
        List<JobEntity> jobEntities = jobRepository.findAll();
        List<Job> jobModels = jobMapper.listModels(jobEntities);
        log.debug("listOutOfService(...)" + jobModels);
        return jobModels;
    }

    @Override
    public Page<Job> findJobPages(int pageNo, int size, String sortField, String sortDirection) {
        log.debug("findJobPages()" + pageNo + "/" + size);
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, size, sort);
        Page<JobEntity> jobPages = jobRepository.findAll(pageable);
        Page<Job> jobs = jobMapper.entitiesJobToModelsPage(jobPages);
        log.debug("findJobPages(...)" + jobs.getNumberOfElements());
        return jobs;
    }

    @Override
    public Page<Job> findByStatusWithPagination(String query, int pageNo, int size, String sortField, String sortDirection) {
        log.debug("findJobPagesWithStatus()" + pageNo + "/" + size);
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, size, sort);
        Page<JobEntity> jobPages = jobRepository.findByStatus(query, pageable);
        Page<Job> jobs = jobMapper.entitiesJobToModelsPage(jobPages);
        log.debug("findJobPagesWithStatus(...)" + jobs.getNumberOfElements());
        return jobs;
    }

    @Override
    public Job createJob(Job job) {
        log.debug("create(" + job + ")");
        job.setRequestDate(LocalDateTime.now());
        job.setOpen(true);
        JobEntity jobEntity = jobMapper.modelToEntity(job);
        JobEntity savedJobEntity = jobRepository.save(jobEntity);
        Job savedJobModel = jobMapper.entityToModel(savedJobEntity);

        log.debug("create(...)");
        return savedJobModel;
    }

    @Override
    public Job findJobById(Long id) throws JobNotFoundException {
        log.debug("read()");
        JobEntity jobEntity = getJobEntity(id);
        Job jobModel = jobMapper.entityToModel(jobEntity);
        log.debug("read(...)" + jobModel);
        return jobModel;
    }


    public Job updateJob(Job job, Long id) throws JobNotFoundException {
        log.debug("update()" + job);
        Optional<JobEntity> optionalJobEntity = jobRepository.findById(id);
        JobEntity jobEntity = optionalJobEntity.orElseThrow(
                () -> new JobNotFoundException("Brak zlecenia o podanym id " + id));

        JobEntity editedJobEntity = jobMapper.modelToEntity(job);
        JobEntity updatedJobEntity = jobRepository.save(editedJobEntity);
        Job updatedJobModel = jobMapper.entityToModel(updatedJobEntity);
        log.debug("update(...) " + updatedJobModel);
        return updatedJobModel;
    }

    @Override
    public void deleteJob(Long id) {
        log.debug("delete()");
        jobRepository.deleteById(id);
        log.debug("delete(...)");
    }

    private JobEntity getJobEntity(Long id) throws JobNotFoundException {
        log.debug("getJobEntity(" + id + ")");
        Optional<JobEntity> optionalJobEntity = jobRepository.findById(id);
        JobEntity jobEntity = optionalJobEntity.orElseThrow(
                () -> new JobNotFoundException("Brak zlecenia o podanym id" + id));
        log.debug("update()" + jobEntity);
        return jobEntity;
    }

    @Override
    public Page<Job> findJobByQuery(int pageNo, int pagesize, String query) {
        log.debug("findJobByQuery()" + query);
        Pageable pageable = PageRequest.of(pageNo - 1, pagesize);
        Page<JobEntity> employeeEntities = jobRepository.searchEmployeeByQuery(pageable, query);
        Page<Job> jobs = jobMapper.entitiesJobToModelsPage(employeeEntities);
        log.debug("findJobByQuery(...)");
        return jobs;
    }

    @Override
    public void deleteAllJobs() {
        log.debug("DeleteAllJobs");
        jobRepository.deleteAll();

    }

    @Override
    public LocalDateTime calculateFutureDate(LocalDateTime initialDate, DateOffset unit, int amount) {
        switch (unit) {
            case SEKUNDY:
                return initialDate.plusSeconds(amount);
            case MINUTY:
                return initialDate.plusSeconds(amount);
            case GODZINY:
                return initialDate.plusSeconds(amount);
            case DNI:
                return initialDate.plusDays(amount);
            case TYGODNIE:
                return initialDate.plusWeeks(amount);
            case MIESIACE:
                return initialDate.plusMonths(amount);
            case LATA:
                return initialDate.plusYears(amount);
            default:
                return initialDate;
        }
    }

    @Override
    public List<Job> getJobList(String query) {
        List<JobEntity> jobEntities = jobRepository.findByQuery(query);
        return jobMapper.listModels(jobEntities);
    }

    @Scheduled(fixedRate = 10000)
    public void checkAndUpdate() {

        List<JobEntity> jobs = jobRepository.findAll();
        jobs.stream().forEach(job -> {

                    if (job.getJobStatus().equals(JobStatus.PRZEGLĄD) && job.getStatus().equalsIgnoreCase("zakończono")) {
                        if (job.getDecision().equals(Decision.TAK) && (job.isOpen())) {

                            LocalDateTime futureJobDate = calculateFutureDate(job.getJobShedule(), job.getDateOffset(), job.getOffset());
                            JobEntity cycleJob = new JobEntity();
                            cycleJob.setMessage(job.getMessage());
                            cycleJob.setMachine(job.getMachine());
                            cycleJob.setStatus("przegląd");
                            cycleJob.setDepartment(job.getDepartment());
                            cycleJob.setEmployee(job.getEmployee());
                            cycleJob.setDecision(job.getDecision());
                            cycleJob.setDateOffset(job.getDateOffset());
                            cycleJob.setJobShedule(futureJobDate);
                            cycleJob.setOffset(job.getOffset());
                            cycleJob.setOpen(true);
                            cycleJob.setJobStatus(JobStatus.PRZEGLĄD);
                            jobRepository.save(cycleJob);
                            job.setOpen(false);
                            jobRepository.save(job);
                        }

                    }
                }
        );
    }
}
