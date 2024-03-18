package pl.medos.cmmsApi.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.model.Task;
import pl.medos.cmmsApi.service.TaskService;

import java.util.List;
import java.util.logging.Logger;

@RestController
@Tag(name = "Tasks")
@RequestMapping("/api/tasks")
public class TaskController {

    private static final Logger LOGGER = Logger.getLogger(TaskController.class.getName());
    private TaskService taskService;
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/list")
    public List<Task> getAllTasks() {
        LOGGER.info("getAllTasks()");
        return taskService.getAllTasks();
    }

    @PostMapping("/add")
    public Task addTask(@RequestBody Task task) {
        LOGGER.info("addTask()");
        return taskService.addTask(task);
    }

    @PutMapping("/edit/{id}")
    public Task editTask(@RequestBody Task task, @PathVariable Long id) {
        LOGGER.info("editTask()");
        return taskService.editTask(task, id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTask(@PathVariable Long id) {
        LOGGER.info("deleteTask()");
        taskService.deleteTask(id);
    }
}
