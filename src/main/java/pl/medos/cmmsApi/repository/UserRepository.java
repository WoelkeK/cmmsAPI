package pl.medos.cmmsApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.medos.cmmsApi.repository.entity.UserEntity;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
}
