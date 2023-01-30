package pl.medos.cmmsApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.medos.cmmsApi.model.Machine;

public interface MachineRepository extends JpaRepository<Machine,Long> {
}
