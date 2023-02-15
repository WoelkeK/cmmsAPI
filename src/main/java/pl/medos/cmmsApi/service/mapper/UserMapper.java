package pl.medos.cmmsApi.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.medos.cmmsApi.model.UserOld;
import pl.medos.cmmsApi.repository.entity.UserEntity;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private static final Logger LOGGER = Logger.getLogger(UserMapper.class.getName());

    private UserEntity userEntity;

    public List<UserOld> listModels(List<UserEntity> userEntities) {

        LOGGER.info("list()" + userEntities);
        List<UserOld> userOldModels = userEntities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());
        return userOldModels;
    }

    public UserOld entityToModel(UserEntity userEntity) {

        LOGGER.info("entityToModel" + userEntity);
        ModelMapper modelMapper = new ModelMapper();
        UserOld userOldModel = modelMapper.map(userEntity, UserOld.class);
//        userModel.setPassword(null);
        return userOldModel;
    }

    public UserEntity modelToEntity(UserOld userOldModel) {

        LOGGER.info("modelToEntity()" + userOldModel);
        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity = modelMapper.map(userOldModel, UserEntity.class);
        return userEntity;
    }
}
