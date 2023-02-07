package pl.medos.cmmsApi.service;

import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.exception.UserNotFoundException;
import pl.medos.cmmsApi.model.User;
import pl.medos.cmmsApi.repository.UserRepository;
import pl.medos.cmmsApi.repository.entity.UserEntity;
import pl.medos.cmmsApi.service.mapper.UserMapper;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserService {

    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    private UserRepository userRepository;
    private UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List list() {
        LOGGER.info("list()");
        List<UserEntity> userEntities = userRepository.findAll();
        List<User> userModels = userMapper.listModels(userEntities);
        LOGGER.info("list(...)");
        return userModels;
    }

    public User create(User user) {
        LOGGER.info("create(" + user + ")");
        UserEntity userEntity = userMapper.modelToEntity(user);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        User savedUserModel = userMapper.entityToModel(savedUserEntity);
        LOGGER.info("create(...)" + savedUserModel);
        return savedUserModel;
    }

    public User read(Long id) throws UserNotFoundException {
        LOGGER.info("read()");
        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
        UserEntity userEntity = optionalUserEntity.orElseThrow(
                () -> new UserNotFoundException("Brak użytkownika o podanym id " + id));
        User userModel = userMapper.entityToModel(userEntity);
        LOGGER.info("read(...)" + userModel);
        return userModel;
    }

    public User update(User user) {
        LOGGER.info("update()" + user);
        UserEntity userEntity = userMapper.modelToEntity(user);
        UserEntity updatedUserEntity = userRepository.save(userEntity);
        User updatedUserModel = userMapper.entityToModel(updatedUserEntity);
        LOGGER.info("update(...) " + updatedUserModel);
        return updatedUserModel;
    }

    public String delete(Long id) {
        LOGGER.info("delete()");
        userRepository.deleteById(id);
        LOGGER.info("delete(...)");
        return "Record " + id + " deleted!";
    }
}
