package pl.medos.cmmsApi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.repository.entity.DepartmentEntity;
import pl.medos.cmmsApi.repository.entity.EmployeeEntity;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    @Query("SELECT p from EmployeeEntity p WHERE " +
            " p.name LIKE CONCAT('%', :query, '%')")
    List<EmployeeEntity> searchEmployeeByName(String query);
    Page<EmployeeEntity> findAll(Pageable pageable);
}
