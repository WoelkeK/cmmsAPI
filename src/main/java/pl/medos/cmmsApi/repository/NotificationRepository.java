package pl.medos.cmmsApi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.medos.cmmsApi.repository.entity.NotificationEntity;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    @Query("SELECT p from NotificationEntity p WHERE CONCAT(p.driverName, ' ', p.supplier, ' ', p.driverPhone, ' ' , p.employee,' ' , p.carPlates,' ' , p.item) LIKE %?1%")
    List<NotificationEntity> searchNotificationsByQuery(String query);
    Page<NotificationEntity>findAll(Pageable pageable);
}
