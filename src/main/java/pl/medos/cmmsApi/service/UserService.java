package pl.medos.cmmsApi.service;


import pl.medos.cmmsApi.dto.UserDto;
import pl.medos.cmmsApi.repository.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
