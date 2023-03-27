package pl.medos.cmmsApi.repository;

import org.apache.xmlbeans.SchemaBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.medos.cmmsApi.repository.entity.ScheduleEntity;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

    @Query("SELECT p from ScheduleEntity p WHERE " +
            " p.name LIKE CONCAT('%', :query, '%')")
    ScheduleEntity searchScheduleByName(String query);

}
