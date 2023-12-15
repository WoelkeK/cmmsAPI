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


//@Query("SELECT p from NotificationEntity p WHERE CONCAT(p.driverName, ' ', p.supplier, ' ', p.driverPhone, ' ' , p.employee,' ' , p.carPlates,' ' , p.item) LIKE %?1%")





    @Query("SELECT p FROM NotificationEntity p WHERE " +
            "LOWER(p.driverName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.supplier) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.employeePhone) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.employee) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.carPlates) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.item) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<NotificationEntity> findByQueryPagable(String keyword, Pageable pageable);
}
