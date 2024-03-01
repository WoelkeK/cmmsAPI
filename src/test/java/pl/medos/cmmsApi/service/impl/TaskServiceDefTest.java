package pl.medos.cmmsApi.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Task;
import pl.medos.cmmsApi.repository.TaskRepository;
import pl.medos.cmmsApi.repository.entity.DepartmentEntity;
import pl.medos.cmmsApi.repository.entity.TaskEntity;
import pl.medos.cmmsApi.service.mapper.TaskMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class TaskServiceDefTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceDef taskService;

    @Test
    void getAllTasks() {

        // 1. Prepare test data
        List<TaskEntity> taskEntities = new ArrayList<>();
        DepartmentEntity departmentEntity1 = new DepartmentEntity(1L, "departmentName1", "location1");
        DepartmentEntity departmentEntity2 = new DepartmentEntity(2L, "departmentName2", "location2");

        taskEntities.add(new TaskEntity(1L, departmentEntity1, departmentEntity2, true, LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        taskEntities.add(new TaskEntity(2L, departmentEntity2, departmentEntity1, true, LocalDateTime.now(), LocalDateTime.now().minusDays(1)));

        List<Task> tasks = new ArrayList<>();

        Department department1 = new Department(1L, "departmentName1", "location1");
        Department department2 = new Department(2L, "departmentName2", "location2");

        tasks.add(new Task(1L, department1, department2, true, LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        tasks.add(new Task(2L, department2, department1, true, LocalDateTime.now(), LocalDateTime.now().minusDays(1)));

        // 2. Stub responses from your dependencies
        Mockito.when(taskRepository.findAll()).thenReturn(taskEntities);
        Mockito.when(taskMapper.toDTO(any(TaskEntity.class))).thenReturn(tasks.get(0));

        // 3. Execute the method under test
        List<Task> result = taskService.getAllTasks();

        // 4. Assert the result
        Assertions.assertAll(
                () -> assertNotNull(result, "Result is NULL"),
                () -> assertEquals(tasks.size(), result.size(), "Returned list size should match"),
                () -> assertTrue(result.size() > 1)
        );
    }

    @Test
    void addTask() {
        Task task = new Task(1L, new Department(), new Department(), true, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        TaskEntity taskEntity = new TaskEntity(1L, new DepartmentEntity(), new DepartmentEntity(), true, LocalDateTime.now(), LocalDateTime.now().plusDays(1));

        Mockito.when(taskMapper.fromDTO(task)).thenReturn(taskEntity);
        Mockito.when(taskRepository.save(taskEntity)).thenReturn(taskEntity);
        Mockito.when(taskMapper.toDTO(taskEntity)).thenReturn(task);

        Task result = taskService.addTask(task);

        Assertions.assertEquals(task, result, "Returned task should match the input task");
    }

    @Test
    void findTaskById() {
        Long id = 1L;
        TaskEntity taskEntity = new TaskEntity(id, new DepartmentEntity(), new DepartmentEntity(), true, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        Task task = new Task(id, new Department(), new Department(), true, LocalDateTime.now(), LocalDateTime.now().plusDays(1));

        Mockito.when(taskRepository.findById(id)).thenReturn(Optional.of(taskEntity));
        Mockito.when(taskMapper.toDTO(taskEntity)).thenReturn(task);

        Task result = taskService.findTaskById(id);

        Assertions.assertEquals(task, result, "Returned task should match the input task");
    }

    @Test
    void deleteTask() {
        Long id = 1L;
        taskService.deleteTask(id);
        Mockito.verify(taskRepository, Mockito.times(1)).deleteById(id);
    }
}