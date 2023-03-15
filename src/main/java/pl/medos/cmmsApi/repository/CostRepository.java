package pl.medos.cmmsApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.medos.cmmsApi.repository.entity.CostEntity;
import pl.medos.cmmsApi.repository.entity.DepartmentEntity;

public interface CostRepository extends JpaRepository<CostEntity, Long> {
    @Query("SELECT p from CostEntity p WHERE " +
            " p.unit LIKE CONCAT('%', :query, '%')")
    CostEntity searchCostByUnit(String query);
}
