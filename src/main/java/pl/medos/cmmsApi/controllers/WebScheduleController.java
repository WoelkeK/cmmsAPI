package pl.medos.cmmsApi.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.*;
import pl.medos.cmmsApi.model.*;
import pl.medos.cmmsApi.service.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/schedules")
@SessionAttributes(names = {"departments", "employees", "machines"})
public class WebScheduleController {

    private static final Logger LOGGER = Logger.getLogger(WebScheduleController.class.getName());

    private ScheduleService scheduleService;
    private EmployeeService employeeService;
    private DepartmentService departmentService;
    private MachineService machineService;


    public WebScheduleController(ScheduleService scheduleService, EmployeeService employeeService, DepartmentService departmentService, MachineService machineService) {
        this.scheduleService = scheduleService;
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.machineService = machineService;
    }

    @GetMapping
    public String listView(Model model) {
        LOGGER.info("listView()");
        List<Schedule> schedules = scheduleService.findAllShedules();
        model.addAttribute("schedules", schedules);
        LOGGER.info("listView(...)");
        return "list-schedule.html";
    }

    @GetMapping(value = "/create")
    public String createView(Model model) {
        LOGGER.info("createView()");
        model.addAttribute("schedule", new Schedule());
        return "create-schedule.html";
    }

    @PostMapping(value = "/create")
    public String create(
            @Valid @ModelAttribute(name = "schedule") Schedule schedule,
            BindingResult result,
            Model model) {
        LOGGER.info("create()" + schedule.getId());

        if (result.hasErrors()) {
            LOGGER.info("create: result has erorr()" + result.getFieldError());
            model.addAttribute("schedule", schedule);
            return "create-schedule";
        }
        model.addAttribute("schedule", schedule);
        scheduleService.createSchedule(schedule);
        LOGGER.info("create(...)");
        return "redirect:/schedules";
    }

    @GetMapping(value = "/read/{id}")
    public String read(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws Exception {
        LOGGER.info("read(" + id + ")");
        Schedule scheduleById = scheduleService.findScheduleById(id);
        modelMap.addAttribute("schedule", scheduleById);
        return "read-schedule.html";
    }

    @GetMapping(value = "/update/{id}")
    public String updateView(
            @PathVariable(name = "id") Long id,
            Model model) throws Exception {
        LOGGER.info("updateView()");
        Schedule scheduleById = scheduleService.findScheduleById(id);
        model.addAttribute("schedule", scheduleById);
        LOGGER.info("updateView(...)" + scheduleById.getId());
        return "update-schedule.html";
    }

    @PostMapping(value = "/update/{id}")
    public String update(@PathVariable(name = "id") Long id,
                         @Valid @ModelAttribute(name = "schedule") Schedule schedule,
                         BindingResult result,
                         Model model) throws ScheduleNotFoundException {
        LOGGER.info("update()" + schedule.getId());

        if (result.hasErrors()) {
            LOGGER.info("update: result has erorr()" + result.getFieldError());
            model.addAttribute("schedule", schedule);
            return "update-schedule";
        }
        model.addAttribute("schedule", schedule);
        Schedule updatedSchedule = scheduleService.updateSchedule(schedule, id);
        LOGGER.info("update(...)");
        return "redirect:/schedules";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(
            @PathVariable(name = "id") Long id) {
        LOGGER.info("delete()");
        scheduleService.deleteSchedule(id);
        return "redirect:/schedules";
    }

    @GetMapping("/search/message")
    public String searchJobsByName(@RequestParam(value = "scheduleMessage") String query,
                                   Model model) {
        LOGGER.info("search()" + query);
        Schedule scheduleByName = scheduleService.findScheduleByName(query);
        model.addAttribute("schedule", scheduleByName);
        return "list-schedules";
    }
}
