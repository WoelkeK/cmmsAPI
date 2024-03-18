package pl.medos.cmmsApi.service.mapper;

import org.mapstruct.Mapper;
import pl.medos.cmmsApi.model.Task;
import pl.medos.cmmsApi.repository.entity.TaskEntity;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    Task toDTO(TaskEntity taskEntity);
    TaskEntity fromDTO(Task task);
}
