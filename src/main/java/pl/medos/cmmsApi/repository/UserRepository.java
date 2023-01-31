package pl.medos.cmmsApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.medos.cmmsApi.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
