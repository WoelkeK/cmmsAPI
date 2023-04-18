package pl.medos.cmmsApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.medos.cmmsApi.repository.entity.ImageEntity;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
}
