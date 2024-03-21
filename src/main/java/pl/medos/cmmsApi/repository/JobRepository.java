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

    @Query("SELECT p from JobEntity p WHERE CONCAT(p.employee.name, ' ', p.machine.model, ' ',p.message , ' ', p.machine.name, ' ', p.department.name) LIKE %?1%")
    Page<JobEntity> searchEmployeeByQuery(Pageable pageable, String query);

    @Query("SELECT p from JobEntity p WHERE CONCAT(p.employee.name, ' ', p.machine.model, ' ',p.message , ' ', p.machine.name, ' ', p.department.name) LIKE %?1%")
    List<JobEntity> findByQuery(String query);
}
