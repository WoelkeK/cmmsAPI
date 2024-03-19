package pl.medos.cmmsApi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.medos.cmmsApi.model.Hardware;
import pl.medos.cmmsApi.repository.entity.HardwareEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface HardwareRepository extends JpaRepository<HardwareEntity, Long> {

    Page<HardwareEntity> findAll(Pageable pageable);

    List<HardwareEntity> findAll(Sort sort);
    @Query("SELECT p FROM HardwareEntity  p WHERE " +
            "LOWER(p.inventoryNo) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.employee) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.serialNumber) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.department) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.ipAddress) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.type) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<HardwareEntity > findHardwareByquery(String keyword);


    @Query("SELECT p FROM HardwareEntity  p WHERE " +
            "LOWER(p.inventoryNo) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.employee) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.serialNumber) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.department) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.ipAddress) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.type) LIKE LOWER(CONCAT('%', :keyword, '%'))")
   Optional<HardwareEntity> findHardwareByIp(String keyword);

    boolean existsByIpAddress(String ipAddress);

//    Boolean existsByIpAddressAndPermission(String clientIp);

   Optional<HardwareEntity> findByIpAddress(String ipAddress);


    @Query("SELECT p FROM HardwareEntity  p WHERE " +
            "LOWER(p.inventoryNo) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.employee) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.serialNumber) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.department) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.ipAddress) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.type) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<HardwareEntity> findByQueryPagable(String keyword, Pageable pageable);

}
