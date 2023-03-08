package pl.medos.cmmsApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.repository.entity.JobEntity;
import pl.medos.cmmsApi.repository.entity.MachineEntity;

import java.util.List;

@Repository
public interface MachineRepository extends JpaRepository<MachineEntity,Long> {
        @Query("SELECT p from MachineEntity p WHERE " +
                " p.name LIKE CONCAT('%', :query, '%')")
        List<MachineEntity> searchMachineByName(String query);

        @Query("SELECT p from MachineEntity p WHERE " +
                " p.department.id LIKE CONCAT('%', :query, '%')")
        List<MachineEntity> searchMachineByDepartment(Long query);
}
