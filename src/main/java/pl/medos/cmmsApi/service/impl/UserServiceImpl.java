//package pl.medos.cmmsApi.service.impl;
//
//
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import pl.medos.cmmsApi.dto.UserDto;
//import pl.medos.cmmsApi.repository.RoleRepository;
//import pl.medos.cmmsApi.repository.UserRepository;
//import pl.medos.cmmsApi.repository.entity.RoleEntity;
//import pl.medos.cmmsApi.repository.entity.UserEntity;
//import pl.medos.cmmsApi.service.UserService;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class UserServiceImpl implements UserService {
//
//    private UserRepository userRepository;
//    private RoleRepository roleRepository;
//    private PasswordEncoder passwordEncoder;
//
//    public UserServiceImpl(UserRepository userRepository,
//                           RoleRepository roleRepository,
//                           PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.roleRepository = roleRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public void saveUser(UserDto userDto) {
//        UserEntity userEntity = new UserEntity();
//        userEntity.setName(userDto.getFirstName() + " " + userDto.getLastName());
//        userEntity.setEmail(userDto.getEmail());
//        // encrypt the password using spring security
//        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
//
//        RoleEntity roleEntity = roleRepository.findByName("ROLE_ADMIN");
//        if(roleEntity == null){
//            roleEntity = checkRoleExist();
//        }
//        userEntity.setRoleEntities(Arrays.asList(roleEntity));
//        userRepository.save(userEntity);
//    }
//
//    @Override
//    public UserEntity findUserByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }
//
//    @Override
//    public List<UserDto> findAllUsers() {
//        List<UserEntity> userEntities = userRepository.findAll();
//        return userEntities.stream()
//                .map((user) -> mapToUserDto(user))
//                .collect(Collectors.toList());
//    }
//
//    private UserDto mapToUserDto(UserEntity userEntity){
//        UserDto userDto = new UserDto();
//        String[] str = userEntity.getName().split(" ");
//        userDto.setFirstName(str[0]);
//        userDto.setLastName(str[1]);
//        userDto.setEmail(userEntity.getEmail());
//        return userDto;
//    }
//
//    private RoleEntity checkRoleExist(){
//        RoleEntity roleEntity = new RoleEntity();
//        roleEntity.setName("ROLE_ADMIN");
//        return roleRepository.save(roleEntity);
//    }
//}
