package pl.medos.cmmsApi.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.medos.cmmsApi.model.Schedule;
import pl.medos.cmmsApi.repository.entity.ScheduleEntity;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class ScheduleMapper {

    private static final Logger LOGGER = Logger.getLogger(SupplierMapper.class.getName());

    public List<ScheduleEntity> listModelsToListEntities(List<Schedule> schedules) {
        LOGGER.info("mapEntitiesToModels()");
        List<ScheduleEntity> scheduleEntities = schedules.stream()
                .map(this::mapModelToEntity)
                .collect(Collectors.toList());

        LOGGER.info("mapEntitiesToModels(...)");
        return scheduleEntities;
    }

    public List<Schedule> listEntitiesToListModels(List<ScheduleEntity> scheduleEntities) {
        LOGGER.info("mapEntitiesToModels()");
        List<Schedule> schedules = scheduleEntities.stream()
                .map(this::mapEntitytoModel)
                .collect(Collectors.toList());
        LOGGER.info("mapEntitiesToModels(...)");
        return schedules;
    }

    public Schedule mapEntitytoModel(ScheduleEntity scheduleEntity) {
        LOGGER.info("mapEntityToModel()");
        ModelMapper modelMapper = new ModelMapper();
        Schedule scheduleModel = modelMapper.map(scheduleEntity, Schedule.class);
        LOGGER.info("mapEntityToModel(...)");
        return scheduleModel;
    }

    public ScheduleEntity mapModelToEntity(Schedule schedule) {
        LOGGER.info("modelToEntity()");
        ModelMapper modelMapper = new ModelMapper();
        ScheduleEntity scheduleEntity = modelMapper.map(schedule, ScheduleEntity.class);
        LOGGER.info("modelToEntity(...)");
        return scheduleEntity;
    }
}
