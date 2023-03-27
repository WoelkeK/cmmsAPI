package pl.medos.cmmsApi.service;

import pl.medos.cmmsApi.exception.ScheduleNotFoundException;
import pl.medos.cmmsApi.model.Schedule;

import java.util.List;

public interface ScheduleService {

    public List<Schedule> findAllShedules();

    public Schedule createSchedule(Schedule schedule);

    public Schedule findScheduleById(Long id) throws ScheduleNotFoundException;

    public Schedule updateSchedule(Schedule schedule, Long id) throws ScheduleNotFoundException;

    public void deleteSchedule(Long id);

    public Schedule findScheduleByName(String name);
}
