package pl.medos.cmmsApi.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.medos.cmmsApi.repository.entity.HardwareEntity;
import pl.medos.cmmsApi.repository.entity.JobEntity;

import java.util.List;
import java.util.Optional;


@Repository
public interface JobRepository extends JpaRepository<JobEntity, Long> {

    Page<JobEntity> findAll(Pageable pageable);

    @Query("SELECT t FROM JobEntity t WHERE LOWER(t.status) LIKE LOWER(CONCAT('%', ?1,'%'))")
    Page<JobEntity> findByStatus(String query, Pageable pageable);


//    @Query("SELECT p FROM JobEntity  p WHERE " +
//            "LOWER(p.employee.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
//            "LOWER(p.engineer.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
//            "LOWER(p.department.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
//            "LOWER(p.machine.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
//            "LOWER(p.message) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
//            "LOWER(p.solution) LIKE LOWER(CONCAT('%', :query, '%'))")
//    @Query(value = "SELECT p from JobEntity p WHERE CONCAT(p.employee.name, ' ', p.engineer.name, ' ', p.department.name, ' ' , p.machine.name,' ' , p.message,' ' , p.solution) LIKE %?1%")
//    List<JobEntity> searchJobsByQuery(String query);


//
//        @Query("SELECT p FROM JobEntity  p WHERE " +
//            "LOWER(p.employee.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
//            "LOWER(p.engineer.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
//            "LOWER(p.department.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
//            "LOWER(p.machine.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
//            "LOWER(p.message) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
//            "LOWER(p.solution) LIKE LOWER(CONCAT('%', :query, '%'))")
//    List<JobEntity> findJobByquery(String query);

    @Query("SELECT p from JobEntity p WHERE CONCAT(p.employee.name, ' ', p.department.name, ' ',p.message ) LIKE %?1%")
    Page<JobEntity> searchEmployeeByQuery(Pageable pageable, String query);
}
