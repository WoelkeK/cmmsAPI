package pl.medos.cmmsApi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.medos.cmmsApi.repository.entity.DepartmentEntity;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity,  Long> {
    @Query("SELECT p from DepartmentEntity p WHERE " +
            " p.name LIKE CONCAT('%', :query, '%')")
    List<DepartmentEntity> searchDepartmentByName(String query);

    Page<DepartmentEntity> findAll(Pageable pageable);

    DepartmentEntity findByName(String name);
}
