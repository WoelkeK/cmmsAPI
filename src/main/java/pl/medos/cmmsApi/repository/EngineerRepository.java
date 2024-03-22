package pl.medos.cmmsApi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.medos.cmmsApi.repository.entity.EmployeeEntity;
import pl.medos.cmmsApi.repository.entity.EngineerEntity;

import java.util.List;

@Repository
public interface EngineerRepository extends JpaRepository<EngineerEntity, Long> {
    @Query("SELECT p from EngineerEntity p WHERE " +
            " p.name LIKE CONCAT('%', :query, '%')")
    List<EngineerEntity> searchEngineerByName(String query);

    @Query("SELECT p from EngineerEntity p WHERE CONCAT(p.name, ' ', p.department.name, ' ',p.position ) LIKE %?1%")
    Page<EngineerEntity> findByQueryPagable(String query, Pageable pageable);

    Page<EngineerEntity> findAll(Pageable pageable);

    @Query("SELECT p from EngineerEntity p WHERE CONCAT(p.name, ' ', p.department.name, ' ',p.position ) LIKE %?1%")
    Page<EngineerEntity> searchEngineerByQuery(Pageable pageable, String query);

    EngineerEntity findByName(String engineer);

    Page<EngineerEntity> findByIsActive(Pageable pageable, Boolean active);
}
