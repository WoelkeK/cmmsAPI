package pl.medos.cmmsApi.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.medos.cmmsApi.repository.entity.JobEntity;

import java.util.List;


@Repository
public interface JobRepository extends JpaRepository<JobEntity, Long> {

    Page<JobEntity> findAll(Pageable pageable);

    @Query("SELECT t FROM JobEntity t WHERE LOWER(t.status) LIKE LOWER(CONCAT('%', ?1,'%'))")
    Page<JobEntity> findByStatus(String query, Pageable pageable);

    @Query("SELECT p from JobEntity p WHERE CONCAT(p.employee.name, ' ', p.engineer.name, ' ', p.department.name, ' ' , p.machine.name,' ' , p.message,' ' , p.solution) LIKE %?1%")
    List<JobEntity> searchJobsByMessage(String query);





//    @Query("SELECT p from JobEntity p WHERE " +
//            " p.department.id LIKE CONCAT('%', :query, '%')")
//    List<JobEntity> searchJobsByDepartment(Long query);
//
//    @Query("SELECT p from JobEntity p WHERE " +
//            " p.employee.id LIKE CONCAT('%', :query, '%')")
//    List<JobEntity> searchJobsByEmployee(Long query);
//
//    @Query("SELECT p from JobEntity p WHERE " +
//            " p.machine.id LIKE CONCAT('%', :query, '%')")
//    List<JobEntity> searchJobsByMachine(Long query);
}
