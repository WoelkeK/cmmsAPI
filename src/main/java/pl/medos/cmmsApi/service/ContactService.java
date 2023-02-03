package pl.medos.cmmsApi.service;

import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.model.Contact;
import pl.medos.cmmsApi.repository.ContactRepository;

import java.util.List;
import java.util.logging.Logger;

@Service
public class ContactService {

    private static final Logger LOGGER = Logger.getLogger(ContactService.class.getName());

    private ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> list() {
        LOGGER.info("list()");
        List<Contact> contacts = contactRepository.findAll();
        LOGGER.info("list(...)");
        return contacts;
    }

    public Contact create(Contact contact) {
        LOGGER.info("create()");
        Contact createdContact = contactRepository.save(contact);
        LOGGER.info("create(...)");
        return createdContact;
    }

    public Contact read(Long id) {
        LOGGER.info("read(" + id + ")");
        Contact readedContact = contactRepository.findById(id).orElseThrow();
        LOGGER.info("read(...)");
        return readedContact;
    }

    public Contact update(Contact contact) {
        LOGGER.info("update()");
        Contact editedContact = contactRepository.findById(contact.getId()).orElseThrow();
        editedContact.setId(contact.getId());
        editedContact.setEmail(contact.getEmail());
        editedContact.setPhoneNb(contact.getPhoneNb());
        Contact createdContact = contactRepository.save(editedContact);
        LOGGER.info("update(...)");
        return createdContact;
    }

    public String delete(Long id) {
        LOGGER.info("delete( " + id + ")");
        contactRepository.deleteById(id);
        LOGGER.info("delete(...)");
        return "Response: 200 Record " + id + "deleted!";
    }
}
