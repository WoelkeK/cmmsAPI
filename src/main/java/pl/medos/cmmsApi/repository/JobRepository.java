package pl.medos.cmmsApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.medos.cmmsApi.model.Job;
@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
}
