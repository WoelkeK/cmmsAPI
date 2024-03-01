package pl.medos.cmmsApi.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import pl.medos.cmmsApi.service.TaskService;

@Controller
@Slf4j
public class WebTaskController {

    private TaskService taskService;

    public WebTaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    public String getAllTassk() {
        return "main-task";
    }

    public String addTask() {
        return "create-task";
    }

    public String findTaskById() {
        return "view-task";
    }

    public void deleteTask() {

    }
}
