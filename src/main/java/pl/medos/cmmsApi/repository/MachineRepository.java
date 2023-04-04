package pl.medos.cmmsApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.repository.entity.JobEntity;
import pl.medos.cmmsApi.repository.entity.MachineEntity;

import java.util.List;

@Repository
public interface MachineRepository extends JpaRepository<MachineEntity, Long> {
    @Query("SELECT p from MachineEntity p WHERE " +
            " p.name LIKE CONCAT('%', :name, '%')")
    List<MachineEntity> searchMachineByName(String name);

    @Query("SELECT p from MachineEntity p WHERE " +
            " p.department.id LIKE CONCAT('%', :id, '%')")
    List<MachineEntity> searchMachineByDepartment(Long id);

    @Query("SELECT p from MachineEntity p WHERE CONCAT(p.name, ' ', p.model, ' ', p.serialNumber, ' ' , p.department.name) LIKE %?1%")
    List<MachineEntity> searchMachineByQuery(String query);

}
