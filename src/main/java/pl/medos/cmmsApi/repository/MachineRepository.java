package pl.medos.cmmsApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.medos.cmmsApi.model.Machine;
import pl.medos.cmmsApi.repository.entity.MachineEntity;
@Repository
public interface MachineRepository extends JpaRepository<MachineEntity,Long> {
}
