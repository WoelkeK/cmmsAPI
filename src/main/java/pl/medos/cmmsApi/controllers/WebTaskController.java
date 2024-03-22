package pl.medos.cmmsApi.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.model.Task;
import pl.medos.cmmsApi.service.TaskService;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/tasks/")
@Slf4j
public class WebTaskController {

    private TaskService taskService;

    public WebTaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("list")
    public String getAllTassk() {
        log.debug("getAllTasks()");
        List<Task> allTasks = taskService.getAllTasks();
        return "main-task";
    }

    @GetMapping("add")
    public String addTaskView(Model model) {
        log.debug("addTaskView()");
        model.addAttribute("task", new Task());
        return "create-task";
    }

    @PostMapping("add")
    public String addTask(@ModelAttribute Task task) {
        log.debug("addTask()");
        taskService.addTask(task);
        return "redirect:/tasks";
    }

    @GetMapping("read/{id}")
    public String findTaskById(@PathVariable Long id, Model model) {
        log.debug("findTaskById()");
        Task task = taskService.findTaskById(id);
        model.addAttribute("task", task);
        return "view-task";
    }

    @GetMapping("edit/{id}")
    public String editTaskView(@PathVariable Long id, Model model) {
        log.debug("editTaskView()");
        Task editedTask = taskService.findTaskById(id);
        model.addAttribute("task", editedTask);
        return "edit-task";
    }

    @PostMapping("edit")
    public String editTask(@ModelAttribute Task task) {
        log.debug("editTask()");
        Task editedTask = taskService.editTask(task, task.getId());
        return "redirect:/tasks";
    }

    @GetMapping("delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        log.debug("delete()");
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }
}
