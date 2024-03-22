package pl.medos.cmmsApi.service.mapper;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.model.Job;
import pl.medos.cmmsApi.repository.entity.EmployeeEntity;
import pl.medos.cmmsApi.repository.entity.JobEntity;
import pl.medos.cmmsApi.repository.entity.MachineEntity;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JobMapper {

    private JobEntity jobEntity;

    public List<Job> listModels(List<JobEntity> jobEntities) {

        log.debug("list()" + jobEntities);
        List<Job> jobModels = jobEntities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());
        return jobModels;
    }

    public Page<Job> entitiesJobToModelsPage(Page<JobEntity> jobPageEntities) {
        log.debug("entitiesJobToModelPage()");
        ModelMapper modelMapper = new ModelMapper();
        Page<Job> jobPageModel = jobPageEntities.map(JobEntity -> modelMapper.map(JobEntity, Job.class));
        log.debug("entitiesJobToModelPage(...)");
        return jobPageModel;
    }

    public Job entityToModel(JobEntity jobEntity) {

        log.debug("entityToModel" + jobEntity);
        ModelMapper modelMapper = new ModelMapper();
        Job jobModel = modelMapper.map(jobEntity, Job.class);
        return jobModel;
    }

    public JobEntity modelToEntity(Job job) {

        log.debug("modelToEntity()" + job);
        ModelMapper modelMapper = new ModelMapper();
        JobEntity jobEntity = modelMapper.map(job, JobEntity.class);
        return jobEntity;
    }
}
