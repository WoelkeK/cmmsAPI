package pl.medos.cmmsApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.medos.cmmsApi.repository.entity.DepartmentEntity;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity,  Long> {
    @Query("SELECT p from DepartmentEntity p WHERE " +
            " p.name LIKE CONCAT('%', :query, '%')")
    DepartmentEntity searchDepartmentByName(String query);
}
