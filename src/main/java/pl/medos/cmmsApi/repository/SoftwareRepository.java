package pl.medos.cmmsApi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.medos.cmmsApi.repository.entity.SoftwareEntity;

import java.util.List;

@Repository
public interface SoftwareRepository extends JpaRepository <SoftwareEntity, Long> {

    @Query("SELECT p from SoftwareEntity p WHERE CONCAT(p.name, ' ' , p.version, ' ', p.number) LIKE %?1%")
    List<SoftwareEntity> searchSoftwareByQuery(String query);

    Page<SoftwareEntity> findAll(Pageable pageable);
    @Query("SELECT t FROM SoftwareEntity t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', ?1,'%'))")
    Page<SoftwareEntity> findByName(String query, Pageable pageable);

}
