package pl.medos.cmmsApi.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.medos.cmmsApi.exception.DepartmentNotFoundException;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.service.DepartmentService;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin
@Slf4j
public class DepartmentController {

    private DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public List list() {
        log.debug("departmentList()");
        List departments = departmentService.findAllDepartments();
        log.debug("departmentList(...)" + departments);
        return departments;
    }

    @PostMapping("/create")
    public Department create(@RequestBody Department department) {
        log.debug("createDepartment(" + department + ")");
        Department createdDepartment = departmentService.createDepartment(department);
        log.debug("createDepartment(...)");
        return createdDepartment;
    }

    @GetMapping("/read/{id}")
    public Department read(@PathVariable(name = "id") Long id) throws DepartmentNotFoundException {
        log.debug("readDepartment(" + id + ")");
        Department readedMachine = departmentService.findDepartmentById(id);
        log.debug("readDepartment(...) " + readedMachine);
        return readedMachine;
    }

    @PutMapping("/update/{id}")
    public Department update(@PathVariable(name = "id") Long id,
                             @RequestBody Department department) throws DepartmentNotFoundException {
        log.debug("updateDepartment(" + department + ")");
        Department updatedMachine = departmentService.updateDepartment(department, id);
        log.debug("updateDepartment(...) " + updatedMachine);
        return updatedMachine;
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        log.debug("deleteDepartment(" + id + ")");
        departmentService.deleteDepartment(id);
        log.debug("deleteDepartment(...)");
    }

    @DeleteMapping("/deleteAll")
    public void delete() {
        log.debug("deleteAllDepartments()");
        departmentService.deleteAll();
        log.debug("deleteAllDepartments(...)");
    }
}
