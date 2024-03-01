package pl.medos.cmmsApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.medos.cmmsApi.repository.entity.TaskEntity;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
