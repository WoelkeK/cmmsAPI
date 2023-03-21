package pl.medos.cmmsApi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.DepartmentNotFoundException;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.service.DepartmentService;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/departments")
public class WebDepartmentController {

    private static final Logger LOGGER = Logger.getLogger(WebDepartmentController.class.getName());
    private DepartmentService departmentService;

    public WebDepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public String listView(ModelMap modelMap) {
        LOGGER.info("listView()");
        List<Department> departments = departmentService.findAllDepartments();
        modelMap.addAttribute("departments", departments);
        LOGGER.info("listView(...)" + departments);
        return "list-department.html";
    }

    @GetMapping(value = "/create")
    public String createView(ModelMap modelMap) {
        LOGGER.info("createView()");
        modelMap.addAttribute("department", new Department());
        List<Department> departments = departmentService.findAllDepartments();
        modelMap.addAttribute("departments", departments);
        return "create-department.html";
    }

    @PostMapping(value = "/create")
    public String create(
            @ModelAttribute(name = "department") Department department) {
        LOGGER.info("create(" + department + ")");
        Department savedDepartment = departmentService.createDepartment(department);
        LOGGER.info("create(...)" + savedDepartment);
        return "redirect:/departments";
    }

    @GetMapping(value = "/read/{id}")
    public String read(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws Exception {
        LOGGER.info("read(" + id + ")");
        Department department = departmentService.findDepartmentById(id);
        modelMap.addAttribute("department", department);
        return "read-department.html";
    }

    @GetMapping(value = "/update/{id}")
    public String updateView(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws Exception {
        LOGGER.info("updateView()");
        Department department = departmentService.findDepartmentById(id);
        modelMap.addAttribute("department", department);
        return "update-department.html";
    }

    @PostMapping(value = "/update")
    public String update(@PathVariable(name = "id") Long id,
                         @ModelAttribute(name = "department") Department department) throws DepartmentNotFoundException {
        LOGGER.info("update()" + department);
        Department updatedDepartment = departmentService.updateDepartment(department, id);
        LOGGER.info("update(...)" + updatedDepartment);
        return "redirect:/departments";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(
            @PathVariable(name = "id") Long id) {
        LOGGER.info("delete()");
        departmentService.deleteDepartment(id);
        return "redirect:/departments";
    }
}
