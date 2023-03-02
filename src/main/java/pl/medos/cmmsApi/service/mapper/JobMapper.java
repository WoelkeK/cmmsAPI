//package pl.medos.cmmsApi.service.mapper;
//
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Component;
//import pl.medos.cmmsApi.model.Job;
//import pl.medos.cmmsApi.repository.entity.JobEntity;
//
//import java.util.List;
//import java.util.logging.Logger;
//import java.util.stream.Collectors;
//
//@Component
//public class JobMapper {
//
//    private static final Logger LOGGER = Logger.getLogger(JobMapper.class.getName());
//      private JobEntity jobEntity;
//
//    public List<Job> listModels(List<JobEntity> jobEntities) {
//
//        LOGGER.info("list()" + jobEntities);
//        List<Job> jobModels = jobEntities.stream()
//                .map(this::entityToModel)
//                .collect(Collectors.toList());
//        return jobModels;
//    }
//
//    public Job entityToModel(JobEntity jobEntity) {
//
//        LOGGER.info("entityToModel" + jobEntity);
//        ModelMapper modelMapper = new ModelMapper();
//        Job jobModel = modelMapper.map(jobEntity, Job.class);
//        return jobModel;
//    }
//
//    public JobEntity modelToEntity(Job job) {
//
//        LOGGER.info("modelToEntity()" + job);
//        ModelMapper modelMapper = new ModelMapper();
//        JobEntity jobEntity = modelMapper.map(job, JobEntity.class);
//        return jobEntity;
//    }
//}
