package pl.medos.cmmsApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.medos.cmmsApi.model.Contact;
import pl.medos.cmmsApi.repository.entity.ContactEntity;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, Long> {
}
