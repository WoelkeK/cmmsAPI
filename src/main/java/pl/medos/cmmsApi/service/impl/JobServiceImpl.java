package pl.medos.cmmsApi.service.impl;

import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.exception.JobNotFoundException;
import pl.medos.cmmsApi.model.Job;
import pl.medos.cmmsApi.repository.JobRepository;
import pl.medos.cmmsApi.repository.entity.JobEntity;
import pl.medos.cmmsApi.service.JobService;
import pl.medos.cmmsApi.service.mapper.JobMapper;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
@Service
public class JobServiceImpl implements JobService {

    private static final Logger LOGGER = Logger.getLogger(JobServiceImpl.class.getName());
    private JobRepository jobRepository;
    private JobMapper jobMapper;

    public JobServiceImpl(JobRepository jobRepository, JobMapper jobMapper) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
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
    public Job createJob(Job job) {

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
        Optional<JobEntity> optionalJobEntity = jobRepository.findById(id);
        JobEntity jobEntity = optionalJobEntity.orElseThrow(
                () -> new JobNotFoundException("Brak zlecenia o podanym id" + id));
        Job jobModel = jobMapper.entityToModel(jobEntity);
        LOGGER.info("read(...)" + jobModel);
        return jobModel;
    }

    @Override
    public Job updateJob(Job job) {
        LOGGER.info("update()" + job);
        JobEntity jobEntity = jobMapper.modelToEntity(job);
        JobEntity updatedJobEntity = jobRepository.save(jobEntity);
        Job updatedJobModel = jobMapper.entityToModel(updatedJobEntity);
        LOGGER.info("update(...) " + updatedJobModel);
        return updatedJobModel;
    }

    @Override
    public void deleteJob(Long id) {
        LOGGER.info("delete()");
        jobRepository.deleteById(id);
        LOGGER.info("delete(...)");
    }
}
