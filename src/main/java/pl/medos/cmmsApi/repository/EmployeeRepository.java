package pl.medos.cmmsApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.medos.cmmsApi.model.Employee;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
