package pl.medos.cmmsApi.service;

import pl.medos.cmmsApi.model.Task;

import java.util.List;

public interface TaskService {

    List<Task> getAllTasks();

    Task addTask(Task task);

    Task editTask(Task task, Long id);

    Task findTaskById(Long id);

    void deleteTask(Long id);
}
