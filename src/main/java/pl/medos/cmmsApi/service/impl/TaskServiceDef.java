package pl.medos.cmmsApi.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.medos.cmmsApi.model.Task;
import pl.medos.cmmsApi.repository.TaskRepository;
import pl.medos.cmmsApi.repository.entity.TaskEntity;
import pl.medos.cmmsApi.service.TaskService;
import pl.medos.cmmsApi.service.mapper.TaskMapper;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskServiceDef implements TaskService {

    private static final Logger LOGGER = Logger.getLogger(TaskServiceDef.class.getName());
    private TaskRepository taskRepository;
    private TaskMapper taskMapper;

    public TaskServiceDef(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public List<Task> getAllTasks() {
        LOGGER.info("getAllTasks()");
        return taskRepository.findAll().stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Task addTask(Task task) {
        LOGGER.info("addTask()");
        TaskEntity taskEntity = taskMapper.fromDTO(task);
        taskRepository.save(taskEntity);
        return taskMapper.toDTO(taskEntity);
    }

    @Override
    public Task editTask(Task task, Long id) {
        LOGGER.info("editTask()");
        Optional<TaskEntity> optionalTaskEntity = taskRepository.findById(id);
        if (optionalTaskEntity.isPresent()) {
            TaskEntity taskEntity = optionalTaskEntity.get();
            taskRepository.save(taskEntity);
            return taskMapper.toDTO(taskEntity);
        } else {
            throw new RuntimeException("Task not found");
        }
    }

    @Override
    public Task findTaskById(Long id) {
        LOGGER.info("findTaskById()");
        Optional<TaskEntity> optionalTaskEntity = taskRepository.findById(id);
        if (optionalTaskEntity.isPresent()) {
            TaskEntity taskEntity = optionalTaskEntity.get();
            return taskMapper.toDTO(taskEntity);
        } else {
            throw new RuntimeException("Task not found");
        }
    }

    @Override
    public void deleteTask(Long id) {
        LOGGER.info("deleteTask()");
        taskRepository.deleteById(id);
    }
}
