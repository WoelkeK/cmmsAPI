package pl.medos.cmmsApi.api;

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
@RequestMapping("/api")
@CrossOrigin
public class DepartmentController {

    private static final Logger LOGGER = Logger.getLogger(DepartmentController.class.getName());

    private DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/departments")
    public List list() {
        LOGGER.info("departmentList()");
        List departments = departmentService.findAllDepartments();
        LOGGER.info("departmentList(...)" + departments);
        return departments;
    }

    @PostMapping("/department")
    public Department create(@RequestBody Department department) {
        LOGGER.info("createDepartment(" + department + ")");
        Department createdDepartment = departmentService.createDepartment(department);
        LOGGER.info("createDepartment(...)");
        return createdDepartment;
    }

    @GetMapping("/department/{id}")
    public Department read(@PathVariable(name = "id") Long id) throws DepartmentNotFoundException {
        LOGGER.info("readDepartment(" + id + ")");
        Department readedMachine = departmentService.findDepartmentById(id);
        LOGGER.info("readDepartment(...) " + readedMachine);
        return readedMachine;
    }

    @PutMapping("/department/{id}")
    public Department update(@PathVariable (name = "id") Long id,
            @RequestBody Department department) throws DepartmentNotFoundException {
        LOGGER.info("updateDepartment(" + department + ")");
        Department updatedMachine = departmentService.updateDepartment(department, id);
        LOGGER.info("updateDepartment(...) " + updatedMachine);
        return updatedMachine;
    }

    @DeleteMapping("/department/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        LOGGER.info("deleteDepartment(" + id + ")");
        departmentService.deleteDepartment(id);
        LOGGER.info("deleteDepartment(...)");
    }
}
