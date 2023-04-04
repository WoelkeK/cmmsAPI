package pl.medos.cmmsApi.service.impl;

import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.exception.ScheduleNotFoundException;
import pl.medos.cmmsApi.model.Schedule;
import pl.medos.cmmsApi.repository.ScheduleRepository;
import pl.medos.cmmsApi.repository.entity.ScheduleEntity;
import pl.medos.cmmsApi.service.ScheduleService;
import pl.medos.cmmsApi.service.mapper.ScheduleMapper;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private static final Logger LOGGER = Logger.getLogger(ScheduleServiceImpl.class.getName());
    private ScheduleRepository scheduleRepository;
    private ScheduleMapper scheduleMapper;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, ScheduleMapper scheduleMapper) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleMapper = scheduleMapper;
    }

    @Override
    public List<Schedule> findAllShedules() {
        LOGGER.info("findAllSchedules()");
        List<ScheduleEntity> schduleEntities = scheduleRepository.findAll();
        List<Schedule> schedules = scheduleMapper.listEntitiesToListModels(schduleEntities);
        LOGGER.info("findAllSchedules(...)");
        return schedules;
    }

    public Schedule createSchedule(Schedule schedule) {
        LOGGER.info("createSchedule()");
        ScheduleEntity scheduleEntity = scheduleMapper.mapModelToEntity(schedule);
        ScheduleEntity savedScheduleEntity = scheduleRepository.save(scheduleEntity);
        Schedule savedSchedule = scheduleMapper.mapEntitytoModel(savedScheduleEntity);
        LOGGER.info("createSchedule(...)");
        return savedSchedule;
    }

    @Override
    public Schedule findScheduleById(Long id) throws ScheduleNotFoundException {
        LOGGER.info("findScheduleById(" + id + ")");
        Optional<ScheduleEntity> optionalScheduleEntity = scheduleRepository.findById(id);
        ScheduleEntity scheduleEntity = optionalScheduleEntity.orElseThrow(
                () -> new ScheduleNotFoundException("Brak jednostki o podanym id")
        );
        Schedule schedule = scheduleMapper.mapEntitytoModel(scheduleEntity);
        LOGGER.info("findScheduleById(...)" + schedule);
        return schedule;
    }

    @Override
    public Schedule updateSchedule(Schedule schedule, Long id) throws ScheduleNotFoundException {
        LOGGER.info("updateSchedules()");
        Optional<ScheduleEntity> optionalScheduleEntity = scheduleRepository.findById(id);
        ScheduleEntity scheduleEntity = optionalScheduleEntity.orElseThrow(
                () -> new ScheduleNotFoundException("Brak jednostki o podanym id")

        );
        ScheduleEntity editedScheduleEntity = scheduleMapper.mapModelToEntity(schedule);
        editedScheduleEntity.setId(scheduleEntity.getId());
        ScheduleEntity updatedScheduleEntity = scheduleRepository.save(editedScheduleEntity);
        Schedule updatedScheduleModel = scheduleMapper.mapEntitytoModel(updatedScheduleEntity);
        LOGGER.info("updateSchedules(...)");
        return updatedScheduleModel;
    }

    @Override
    public void deleteSchedule(Long id) {
        LOGGER.info("deleteSchedules()");
        scheduleRepository.deleteById(id);
        LOGGER.info("deleteSchedules(...)");
    }

    @Override
    public Schedule findScheduleByName(String name) {
        LOGGER.info("findScheduleByName()");
        ScheduleEntity scheduleEntity = scheduleRepository.searchScheduleByName(name);
        Schedule schedule = scheduleMapper.mapEntitytoModel(scheduleEntity);
        LOGGER.info("findScheduleByName(...)");
        return schedule;
    }
}
