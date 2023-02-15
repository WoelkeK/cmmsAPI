//package pl.medos.cmmsApi.service;
//
//import org.springframework.stereotype.Service;
//import pl.medos.cmmsApi.exception.ContactNotFoundException;
//import pl.medos.cmmsApi.model.Contact;
//import pl.medos.cmmsApi.repository.ContactRepository;
////import pl.medos.cmmsApi.repository.entity.ContactEntity;
//import pl.medos.cmmsApi.service.mapper.ContacttMapper;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.logging.Logger;
//
//@Service
//public class ContactService {
//
//    private static final Logger LOGGER = Logger.getLogger(ContactService.class.getName());
//
//    private ContactRepository contactRepository;
//    private ContacttMapper contacttMapper;
//
//    public ContactService(ContactRepository contactRepository, ContacttMapper contacttMapper) {
//        this.contactRepository = contactRepository;
//        this.contacttMapper = contacttMapper;
//    }
//
//    public List<Contact> list() {
//        LOGGER.info("list()");
//        List<ContactEntity> contactEntities = contactRepository.findAll();
//        List<Contact> contactModels = contacttMapper.listModels(contactEntities);
//        LOGGER.info("list(...)" + contactModels);
//        return contactModels;
//    }
//
//    public Contact create(Contact contact) {
//        LOGGER.info("create()" + contact);
//        ContactEntity contactEntity = contacttMapper.modelToEntity(contact);
//        ContactEntity savedContactEntity = contactRepository.save(contactEntity);
//        Contact savedContactModel = contacttMapper.entityToModel(savedContactEntity);
//        LOGGER.info("create(...)" + savedContactModel);
//        return savedContactModel;
//    }
//
//    public Contact read(Long id) throws ContactNotFoundException {
//        LOGGER.info("read(" + id + ")");
//        Optional<ContactEntity> optionalContactEntity = contactRepository.findById(id);
//        ContactEntity contactEntity = optionalContactEntity.orElseThrow(
//                () -> new ContactNotFoundException("Brak kontaktu o podanym id" + id)
//        );
//        Contact savedContactModel = contacttMapper.entityToModel(contactEntity);
//        LOGGER.info("read(...)" + savedContactModel);
//        return savedContactModel;
//    }
//
//    public Contact update(Contact contact) {
//        LOGGER.info("update()" + contact);
//        ContactEntity contactEntity = contacttMapper.modelToEntity(contact);
//        ContactEntity updatedContactEntity = contactRepository.save(contactEntity);
//        Contact updatedContactModel = contacttMapper.entityToModel(updatedContactEntity);
//        LOGGER.info("update(...)" + updatedContactModel);
//        return updatedContactModel;
//    }
//
//    public String delete(Long id) {
//        LOGGER.info("delete( " + id + ")");
//        contactRepository.deleteById(id);
//        LOGGER.info("delete(...)");
//        return "Response: 200 Record " + id + "deleted!";
//    }
//}
