package pl.medos.cmmsApi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.medos.cmmsApi.exception.DepartmentNotFoundException;
import pl.medos.cmmsApi.model.Department;

import java.util.List;

public interface DepartmentService {

    List<Department> findAllDepartments();

    Department createDepartment(Department department);

    Department findDepartmentById(Long id) throws DepartmentNotFoundException;

    Department updateDepartment(Department department, Long id) throws DepartmentNotFoundException;

    void deleteDepartment(Long id);

    Department findDepartmentByName(String departmentName);

    Page<Department> findDepartmentPage(int pageNo, int size, String sortField, String sortDirection);

    Department findByName(String name);

    void deleteAll();

}
