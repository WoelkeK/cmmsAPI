//package pl.medos.cmmsApi.service;
//
//import org.springframework.stereotype.Service;
//import pl.medos.cmmsApi.model.User;
//import pl.medos.cmmsApi.repository.UserRepository;
//
//import java.util.List;
//import java.util.logging.Logger;
//
//@Service
//public class UserService {
//
//    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());
//
//    private UserRepository userRepository;
//
//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    public List listAll() {
//        LOGGER.info("listAll()");
//        List<User> userList = userRepository.findAll();
//        LOGGER.info("ListAll(...)");
//        return userList;
//
//    }
//
//    public User create(User user) {
//        LOGGER.info("create(" + user + ")");
//        User createdUser = userRepository.save(user);
//        LOGGER.info("create(...)");
//        return createdUser;
//
//    }
//
//    public User read(Long id) {
//        LOGGER.info("read()");
//        User readedUser = userRepository.findById(id).orElseThrow();
//        LOGGER.info("read(...)");
//        return readedUser;
//
//    }
//
//    public User update(User user) {
//        LOGGER.info("update()");
//        User editedUser = userRepository.findById(user.getId()).orElseThrow();
//        editedUser.setLogin(user.getLogin());
//        editedUser.setRole(user.getRole());
//        editedUser.setPassword(user.getPassword());
//
//        User updatedUser = userRepository.save(editedUser);
//        LOGGER.info("update(...) " + updatedUser);
//        return updatedUser;
//    }
//
//    public String delete(Long id) {
//        LOGGER.info("delete()");
//        userRepository.deleteById(id);
//        LOGGER.info("delete(...)");
//        return "Record " + id + " deleted!";
//    }
//}
