package pl.medos.cmmsApi.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.medos.cmmsApi.repository.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
