//package pl.medos.cmmsApi.service.mapper;
//
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Component;
//import pl.medos.cmmsApi.model.Contact;
//import pl.medos.cmmsApi.model.Department;
//import pl.medos.cmmsApi.repository.entity.ContactEntity;
//import pl.medos.cmmsApi.repository.entity.DepartmentEntity;
//
//import java.util.List;
//import java.util.logging.Logger;
//import java.util.stream.Collectors;
//
//@Component
//public class ContacttMapper {
//
//    private static final Logger LOGGER = Logger.getLogger(ContacttMapper.class.getName());
//
//    private ContactEntity contactEntity;
//
//    public List<Contact> listModels(List<ContactEntity> contactEntities) {
//
//        LOGGER.info("list()" + contactEntities);
//        List<Contact> contactModels = contactEntities.stream()
//                .map(this::entityToModel)
//                .collect(Collectors.toList());
//        return contactModels;
//    }
//
//    public Contact entityToModel(ContactEntity contactEntity) {
//
//        LOGGER.info("entityToModel" + contactEntity);
//        ModelMapper modelMapper = new ModelMapper();
//        Contact contactModel = modelMapper.map(contactEntity, Contact.class);
//        return contactModel;
//    }
//
//    public ContactEntity modelToEntity(Contact contact) {
//
//        LOGGER.info("modelToEntity()" + contact);
//        ModelMapper modelMapper = new ModelMapper();
//        ContactEntity contactEntity = modelMapper.map(contact, ContactEntity.class);
//        return contactEntity;
//    }
//}
