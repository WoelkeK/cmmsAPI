package pl.medos.cmmsApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.medos.cmmsApi.repository.entity.MachineEntity;

import java.io.Serializable;

public interface RaportService extends JpaRepository<MachineEntity, Serializable> {
}
