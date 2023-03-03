package pl.medos.cmmsApi.service;


import pl.medos.cmmsApi.dto.UserDto;
import pl.medos.cmmsApi.repository.entity.UserEntity;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    UserEntity findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
