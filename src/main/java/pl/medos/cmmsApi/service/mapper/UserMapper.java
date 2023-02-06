package pl.medos.cmmsApi.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.medos.cmmsApi.model.User;
import pl.medos.cmmsApi.repository.entity.UserEntity;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private static final Logger LOGGER = Logger.getLogger(UserMapper.class.getName());

    private UserEntity userEntity;

    public List<User> listModels(List<UserEntity> userEntities) {

        LOGGER.info("list()" + userEntities);
        List<User> userModels = userEntities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());
        return userModels;
    }

    public User entityToModel(UserEntity userEntity) {

        LOGGER.info("entityToModel" + userEntity);
        ModelMapper modelMapper = new ModelMapper();
        User userModel = modelMapper.map(userEntity, User.class);
//        userModel.setPassword(null);
        return userModel;
    }

    public UserEntity modelToEntity(User userModel) {

        LOGGER.info("modelToEntity()" + userModel);
        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity = modelMapper.map(userModel, UserEntity.class);
        return userEntity;
    }
}
