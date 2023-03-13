package pl.medos.cmmsApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.medos.cmmsApi.repository.entity.CostEntity;

public interface CostRepository extends JpaRepository<CostEntity, Long> {
}
