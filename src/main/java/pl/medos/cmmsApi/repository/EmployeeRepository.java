package pl.medos.cmmsApi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.medos.cmmsApi.repository.entity.EmployeeEntity;
import pl.medos.cmmsApi.repository.entity.JobEntity;
import pl.medos.cmmsApi.repository.entity.MachineEntity;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    @Query("SELECT p from EmployeeEntity p WHERE CONCAT(p.name, ' ', p.department.name, ' ',p.position ) LIKE %?1%")
    List<EmployeeEntity> searchEmployeeByQuery(String query);

    Page<EmployeeEntity> findAll(Pageable pageable);
}
