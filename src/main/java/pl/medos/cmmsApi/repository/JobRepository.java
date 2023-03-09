package pl.medos.cmmsApi.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.medos.cmmsApi.repository.entity.JobEntity;

import java.util.List;



public interface JobRepository extends JpaRepository<JobEntity, Long> {

    @Query("SELECT p from JobEntity p WHERE " +
            " p.message LIKE CONCAT('%', :query, '%')")
    List<JobEntity> searchJobsByMessage(String query);

    @Query("SELECT p from JobEntity p WHERE " +
            " p.department.id LIKE CONCAT('%', :query, '%')")
    List<JobEntity> searchJobsByDepartment(Long query);

    @Query("SELECT p from JobEntity p WHERE " +
            " p.employee.id LIKE CONCAT('%', :query, '%')")
    List<JobEntity> searchJobsByEmployee(Long query);

    @Query("SELECT p from JobEntity p WHERE " +
            " p.machine.id LIKE CONCAT('%', :query, '%')")
    List<JobEntity> searchJobsByMachine(Long query);
}
