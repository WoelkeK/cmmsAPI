package pl.medos.cmmsApi.service;

import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.exception.JobNotFoundException;
import pl.medos.cmmsApi.model.Job;
import pl.medos.cmmsApi.repository.JobRepository;
import pl.medos.cmmsApi.repository.entity.JobEntity;
import pl.medos.cmmsApi.service.mapper.JobMapper;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class JobService {

    private static final Logger LOGGER = Logger.getLogger(JobService.class.getName());

    private JobRepository jobRepository;
    private JobMapper jobMapper;

    public JobService(JobRepository jobRepository, JobMapper jobMapper) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
    }

    public List<Job> list() {

        LOGGER.info("list()");
        List<JobEntity> jobEntities = jobRepository.findAll();
        LOGGER.info("Logout requestTimeEntity " + jobEntities);
        List<Job> jobModels = jobMapper.listModels(jobEntities);
        LOGGER.info("listOutOfService(...)" + jobModels);
        return jobModels;
    }

    public Job create(Job job) {

        LOGGER.info("create(" + job + ")");
        JobEntity jobEntity = jobMapper.modelToEntity(job);
        JobEntity savedJobEntity = jobRepository.save(jobEntity);
        Job savedJobModel = jobMapper.entityToModel(savedJobEntity);
        LOGGER.info("create(...)");
        return savedJobModel;
    }

    public Job read(Long id) throws JobNotFoundException {

        LOGGER.info("read()");
        Optional<JobEntity> optionalJobEntity = jobRepository.findById(id);
        JobEntity jobEntity = optionalJobEntity.orElseThrow(
                () -> new JobNotFoundException("Brak zlecenia o podanym id" + id));
        Job jobModel = jobMapper.entityToModel(jobEntity);
        LOGGER.info("read(...)" + jobModel);
        return jobModel;
    }

    public Job update(Job job) {

        LOGGER.info("update()" + job);
        JobEntity jobEntity = jobMapper.modelToEntity(job);
        JobEntity updatedJobEntity = jobRepository.save(jobEntity);
        Job updatedJobModel = jobMapper.entityToModel(updatedJobEntity);
        LOGGER.info("update(...) " + updatedJobModel);
        return updatedJobModel;
    }

    public String delete(Long id) {

        LOGGER.info("delete()");
        jobRepository.deleteById(id);
        LOGGER.info("delete(...)");
        return "Response: 200 Record" + id + " deleted!";
    }
}
