package pl.medos.cmmsApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.repository.entity.DepartmentEntity;
@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity,  Long> {
}
