//package pl.medos.cmmsApi.service;
//
//import org.springframework.stereotype.Service;
//import pl.medos.cmmsApi.exception.UserNotFoundException;
//import pl.medos.cmmsApi.model.UserOld;
//import pl.medos.cmmsApi.repository.UserRepositoryOld;
//import pl.medos.cmmsApi.repository.entity.UserEntity;
//import pl.medos.cmmsApi.service.mapper.UserMapper;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.logging.Logger;
//
//@Service
//public class UserService {
//
//    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());
//
//    private UserRepositoryOld userRepositoryOld;
//    private UserMapper userMapper;
//
//    public UserService(UserRepositoryOld userRepositoryOld, UserMapper userMapper) {
//        this.userRepositoryOld = userRepositoryOld;
//        this.userMapper = userMapper;
//    }
//
//    public List list() {
//        LOGGER.info("list()");
//        List<UserEntity> userEntities = userRepositoryOld.findAll();
//        List<UserOld> userOldModels = userMapper.listModels(userEntities);
//        LOGGER.info("list(...)");
//        return userOldModels;
//    }
//
//    public UserOld create(UserOld userOld) {
//        LOGGER.info("create(" + userOld + ")");
//        UserEntity userEntity = userMapper.modelToEntity(userOld);
//        UserEntity savedUserEntity = userRepositoryOld.save(userEntity);
//        UserOld savedUserModelOld = userMapper.entityToModel(savedUserEntity);
//        LOGGER.info("create(...)" + savedUserModelOld);
//        return savedUserModelOld;
//    }
//
//    public UserOld read(Long id) throws UserNotFoundException {
//        LOGGER.info("read()");
//        Optional<UserEntity> optionalUserEntity = userRepositoryOld.findById(id);
//        UserEntity userEntity = optionalUserEntity.orElseThrow(
//                () -> new UserNotFoundException("Brak użytkownika o podanym id " + id));
//        UserOld userOldModel = userMapper.entityToModel(userEntity);
//        LOGGER.info("read(...)" + userOldModel);
//        return userOldModel;
//    }
//
//    public UserOld update(UserOld userOld) {
//        LOGGER.info("update()" + userOld);
//        UserEntity userEntity = userMapper.modelToEntity(userOld);
//        UserEntity updatedUserEntity = userRepositoryOld.save(userEntity);
//        UserOld updatedUserModelOld = userMapper.entityToModel(updatedUserEntity);
//        LOGGER.info("update(...) " + updatedUserModelOld);
//        return updatedUserModelOld;
//    }
//
//    public String delete(Long id) {
//        LOGGER.info("delete()");
//        userRepositoryOld.deleteById(id);
//        LOGGER.info("delete(...)");
//        return "Record " + id + " deleted!";
//    }
//}
