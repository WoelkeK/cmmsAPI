package pl.medos.cmmsApi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.medos.cmmsApi.repository.entity.HardwareEntity;
import pl.medos.cmmsApi.repository.entity.MachineEntity;

import java.util.List;

@Repository
public interface HardwareRepository extends JpaRepository<HardwareEntity, Long> {
    @Query("SELECT p from HardwareEntity p WHERE " +
            " p.name LIKE CONCAT('%', :name, '%')")
    List<HardwareEntity> searchHardwareByName(String name);

    @Query("SELECT p from HardwareEntity p WHERE " +
            " p.department.id LIKE CONCAT('%', :id, '%')")
    List<HardwareEntity> searchHardwareByDepartment(Long id);

    @Query("SELECT p from HardwareEntity p WHERE CONCAT(" +
            "p.name, ' ', p.employee.name, ' ', p.serialNumber, ' ' , p.department.name, ' ' , p.macAddress, ' ' , p.ipAddress, ' ' , p.type, ' ', p.department) LIKE %?1%")
    List<HardwareEntity> searchHardwareByQuery(String query);
    Page<HardwareEntity> findAll(Pageable pageable);
}