package pl.medos.cmmsApi.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.medos.cmmsApi.exception.ContactNotFoundException;
import pl.medos.cmmsApi.model.Contact;
import pl.medos.cmmsApi.service.ContactService;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class ContactController {

    private static final Logger LOGGER = Logger.getLogger(ContactController.class.getName());

    private ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/contact")
    public List<Contact> list() {
        LOGGER.info("list()");
        List<Contact> contacts = contactService.list();
        LOGGER.info("list(...)");
        return contacts;
    }

    @PostMapping("/contact")
    public Contact create(@RequestBody Contact contact) {
        LOGGER.info("create()");
        Contact createdContact = contactService.create(contact);
        LOGGER.info("create(...)" + createdContact);
        return createdContact;
    }

    @GetMapping("/contact/{id}")
    public Contact read(Long id) throws ContactNotFoundException {
        LOGGER.info("read(" + id + ")");
        Contact readedContact = contactService.read(id);
        LOGGER.info("read(...)" + readedContact);
        return readedContact;
    }

    @PutMapping("/contact")
    public Contact update(@RequestBody Contact contact) {
        LOGGER.info("update()");
        Contact updatedContact = contactService.update(contact);
        LOGGER.info("update(...)" + updatedContact);
        return updatedContact;
    }

    @DeleteMapping("/contact/{id}")
    public String delete(Long id) {
        LOGGER.info("delete(" + id + ")");
        String deletedMessage = contactService.delete(id);
        LOGGER.info("delete(...)");
        return deletedMessage;
    }
}
