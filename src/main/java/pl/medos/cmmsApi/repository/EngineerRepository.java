package pl.medos.cmmsApi.repository;

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
    List<EngineerEntity> searchEmployeeByName(String query);
}
