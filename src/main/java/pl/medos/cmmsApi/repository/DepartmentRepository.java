package pl.medos.cmmsApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.medos.cmmsApi.model.Department;

public interface DepartmentRepository extends JpaRepository<Department,  Long> {
}
