package pl.medos.cmmsApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.medos.cmmsApi.model.Sensor;
@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {
}
