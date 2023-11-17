package pl.medos.cmmsApi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.medos.cmmsApi.repository.entity.NotificationEntity;
import pl.medos.cmmsApi.repository.entity.PassEntity;

import java.util.List;

@Repository
public interface PassRepository extends JpaRepository<PassEntity, Long> {

    @Query("SELECT p from PassEntity p WHERE CONCAT(p.name, ' ', p.phone, ' ', p.company, ' ' , p.plates) LIKE %?1%")
    List<PassEntity> searchPassesByQuery(String query);
    Page<PassEntity> findAll(Pageable pageable);
}
