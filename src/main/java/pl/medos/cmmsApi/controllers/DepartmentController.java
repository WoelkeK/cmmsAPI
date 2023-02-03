package pl.medos.cmmsApi.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.service.DepartmentService;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class DepartmentController {

    private static final Logger LOGGER = Logger.getLogger(DepartmentController.class.getName());

    private DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/department")
    public List allDepartmentList() {
        LOGGER.info("allDepartmentList()");
        List allDepartment = departmentService.listAll();
        LOGGER.info("allDepartmentList(...)" + allDepartment);
        return allDepartment;
    }

    @PostMapping("/department")
    public Department createDepartment(@RequestBody Department department) {
        LOGGER.info("createDepartment(" + department + ")");
        Department createdDepartment = departmentService.create(department);
        LOGGER.info("createDepartment(...)");
        return createdDepartment;
    }

    @GetMapping("/department/{id}")
    public Department readDepartment(@PathVariable(name = "id") Long id) {
        LOGGER.info("readDepartment(" + id + ")");
        Department readedMachine = departmentService.read(id);
        LOGGER.info("readDepartment(...) " + readedMachine);
        return readedMachine;
    }

    @PutMapping("/department")
    public Department updateDepartment(@RequestBody Department department) {
        LOGGER.info("updateDepartment(" + department + ")");
        Department updatedMachine = departmentService.update(department);
        LOGGER.info("updateDepartment(...) " + updatedMachine);
        return updatedMachine;
    }

    @DeleteMapping("/department/{id}")
    public String deleteDepartment(@PathVariable(name = "id") Long id) {
        LOGGER.info("deleteDepartment(" + id + ")");
        String deleteMessage = departmentService.delete(id);
        LOGGER.info("deleteDepartment(...)");
        return deleteMessage;
    }
}