package pl.medos.cmmsApi.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.exception.JobNotFoundException;
import pl.medos.cmmsApi.exception.MachineNotFoundException;
import pl.medos.cmmsApi.model.*;
import pl.medos.cmmsApi.repository.JobRepository;
import pl.medos.cmmsApi.repository.entity.JobEntity;
import pl.medos.cmmsApi.repository.entity.MachineEntity;
import pl.medos.cmmsApi.service.ImageService;
import pl.medos.cmmsApi.service.JobService;
import pl.medos.cmmsApi.service.mapper.JobMapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@Slf4j
public class JobServiceImpl implements JobService {

    private static final Logger LOGGER = Logger.getLogger(JobServiceImpl.class.getName());
    private JobRepository jobRepository;
    private JobMapper jobMapper;

    private ImageService imageService;

    public JobServiceImpl(JobRepository jobRepository, JobMapper jobMapper,ImageService imageService) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
        this.imageService = imageService;
    }

    @Override
    public List<Job> findAllJobs() {
        LOGGER.info("list()");
        List<JobEntity> jobEntities = jobRepository.findAll();
        LOGGER.info("show requestTimeEntity " + jobEntities);
        List<Job> jobModels = jobMapper.listModels(jobEntities);
        LOGGER.info("listOutOfService(...)" + jobModels);
        return jobModels;
    }

    @Override
    public Page<Job> findJobPages(int pageNo, int size, String sortField, String sortDirection) {
        LOGGER.info("findJobPages()" + pageNo + "/" + size);
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).ascending(): Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, size, sort);
        LOGGER.info("Pageable() " + pageable.getPageNumber()+ "/" + pageable.getPageSize());
        Page<JobEntity> jobPages = jobRepository.findAll(pageable);
        LOGGER.info("findJobPages(repo)" +jobPages.getNumberOfElements());
        Page<Job> jobs = jobMapper.entitiesJobToModelsPage(jobPages);
        LOGGER.info("findJobPages(...)" +jobs.getNumberOfElements());
        return jobs;
    }

    @Override
    public Page<Job> findByStatusWithPagination(String query, int pageNo, int size, String sortField, String sortDirection) {
        LOGGER.info("findJobPagesWithStatus()" + pageNo + "/" + size);
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).ascending(): Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, size, sort);
        LOGGER.info("Pageable() " + pageable.getPageNumber()+ "/" + pageable.getPageSize());
        Page<JobEntity> jobPages = jobRepository.findByStatus(query,pageable);
        LOGGER.info("findJobPagesWithStatus(repo)" +jobPages.getNumberOfElements());
        Page<Job> jobs = jobMapper.entitiesJobToModelsPage(jobPages);
        LOGGER.info("findJobPagesWithStatus(...)" +jobs.getNumberOfElements());
        return jobs;
    }
    @Override
    public Job createJob(Job job) throws IOException {
        LOGGER.info("create(" + job + ")");
        JobEntity jobEntity = jobMapper.modelToEntity(job);
        JobEntity savedJobEntity = jobRepository.save(jobEntity);
        Job savedJobModel = jobMapper.entityToModel(savedJobEntity);

        LOGGER.info("create(...)");
        return savedJobModel;
    }

    @Override
    public Job findJobById(Long id) throws JobNotFoundException {
        LOGGER.info("read()");
        JobEntity jobEntity = getJobEntity(id);
        Job jobModel = jobMapper.entityToModel(jobEntity);
        LOGGER.info("read(...)" + jobModel);
        return jobModel;
    }


    public Job updateJob(Job job, Long id) throws JobNotFoundException {
        LOGGER.info("update()" + job);
        Optional<JobEntity> optionalJobEntity = jobRepository.findById(id);
        JobEntity jobEntity = optionalJobEntity.orElseThrow(
                () -> new JobNotFoundException("Brak zlecenia o podanym id " + id));

        JobEntity editedJobEntity = jobMapper.modelToEntity(job);
        JobEntity updatedJobEntity = jobRepository.save(editedJobEntity);
        Job updatedJobModel = jobMapper.entityToModel(updatedJobEntity);
        LOGGER.info("update(...) " + updatedJobModel);
        return updatedJobModel;
    }

//    @Override
//    public Job updateJob(Job job, Long id) throws JobNotFoundException {
//        LOGGER.info("update()" + job);
//        JobEntity readedJobEntity = getJobEntity(id);
//        JobEntity editedJobEntity = jobMapper.modelToEntity(job);
//        editedJobEntity.setId(readedJobEntity.getId());

//        LOGGER.info("editedJobentity()" + readedJobEntity.getId() + "in/out " + editedJobEntity.getId());
//        Cost cost = costService.searchCostByUnit("h");
//        LOGGER.info("cost" + cost.getUnit());
//        LocalDateTime jobStartTime = job.getJobStartTime();
//        LocalDateTime jobStopTime = job.getJobStopTime();
//        long minutes = ChronoUnit.MINUTES.between(jobStartTime, jobStopTime);
//        double jobTimeCost = (double) Math.round(((cost.getNetCost() / 60) * minutes) * 100) / 100;
//        editedJobEntity.setCalcCost(jobTimeCost);

//        LOGGER.info("editedCostCalc " + editedJobEntity.getCalcCost());
//        JobEntity updatedJobEntity = jobRepository.save(editedJobEntity);
//        Job updatedJobModel = jobMapper.entityToModel(updatedJobEntity);
//        LOGGER.info("update(...) " + updatedJobModel);
//        return updatedJobModel;
//    }

    @Override
    public void deleteJob(Long id) {
        LOGGER.info("delete()");
        jobRepository.deleteById(id);
        LOGGER.info("delete(...)");
    }

    private JobEntity getJobEntity(Long id) throws JobNotFoundException {
        LOGGER.info("getJobEntity(" + id + ")");
        Optional<JobEntity> optionalJobEntity = jobRepository.findById(id);
        JobEntity jobEntity = optionalJobEntity.orElseThrow(
                () -> new JobNotFoundException("Brak zlecenia o podanym id" + id));
        LOGGER.info("update()" + jobEntity);
        return jobEntity;
    }

    @Override
    public Page<Job> findJobByQuery(int pageNo, int pagesize, String query) {
        LOGGER.info("findJobByQuery()" + query);
        Pageable pageable = PageRequest.of(pageNo-1, pagesize);
        Page<JobEntity> employeeEntities = jobRepository.searchEmployeeByQuery(pageable,query);
        Page<Job> jobs = jobMapper.entitiesJobToModelsPage(employeeEntities);
        LOGGER.info("findJobByQuery(...)");
        return jobs;
    }

}
