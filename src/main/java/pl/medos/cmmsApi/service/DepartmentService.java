package pl.medos.cmmsApi.service;

import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.repository.DepartmentRepository;

import java.util.List;
import java.util.logging.Logger;

@Service
public class DepartmentService {

    private static final Logger LOGGER = Logger.getLogger(DepartmentService.class.getName());

    private DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List listAll() {
        LOGGER.info("listAll()");
        List<Department> departmentList = departmentRepository.findAll();
        LOGGER.info("ListAll(...)");
        return departmentList;
    }

    public Department create(Department department) {
        LOGGER.info("create(" + department + ")");
        Department createdDepartment = departmentRepository.save(department);
        LOGGER.info("create(...)");
        return createdDepartment;
    }

    public Department read(Long id) {
        LOGGER.info("read()");
        Department readedDepartment = departmentRepository.findById(id).orElseThrow();
        LOGGER.info("read(...)");
        return readedDepartment;
    }

    public Department update(Department department) {
        LOGGER.info("update()");
        Department editedDepartment = departmentRepository.findById(department.getId()).orElseThrow();
        editedDepartment.setName(department.getName());
        editedDepartment.setLocation(department.getLocation());
        Department updateDepartment = departmentRepository.save(editedDepartment);
        LOGGER.info("update(...) " + department);
        return updateDepartment;
    }

    public String delete(Long id) {
        LOGGER.info("delete()");
        departmentRepository.deleteById(id);
        LOGGER.info("delete(...)");
        return "Record " + id + " deleted!";
    }
}
