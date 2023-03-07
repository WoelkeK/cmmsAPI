package pl.medos.cmmsApi.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.medos.cmmsApi.repository.entity.JobEntity;


public interface JobRepository extends JpaRepository<JobEntity, Long> {
//
//    Optional<JobEntity> findByDepartment(String department);
//    @Query("SELECT p from Jobs p WHERE " +
//            " p.machine.name LIKE CONCAT('%', :query, '%')")
//    List<JobEntity> searchJobs(String query)
}
