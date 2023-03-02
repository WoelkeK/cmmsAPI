package pl.medos.cmmsApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.medos.cmmsApi.repository.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
