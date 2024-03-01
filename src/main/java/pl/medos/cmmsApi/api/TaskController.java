package pl.medos.cmmsApi.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.model.Task;
import pl.medos.cmmsApi.service.TaskService;

import java.util.List;

@RestController
@Tag(name = "Task")
@Slf4j
@RequestMapping("/api/tasks")
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/list")
    public List<Task> getAllTasks() {
        log.debug("getAllTasks()");
        return taskService.getAllTasks();
    }

    @PostMapping("/add")
    public Task addTask(@RequestBody Task task) {
        log.debug("addTask()");
        return taskService.addTask(task);
    }

    @PutMapping("/edit/{id}")
    public Task editTask(@RequestBody Task task, @PathVariable Long id) {
        log.debug("editTask()");
        return taskService.editTask(task, id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTask(@PathVariable Long id) {
        log.debug("deleteTask()");
        taskService.deleteTask(id);
    }
}
