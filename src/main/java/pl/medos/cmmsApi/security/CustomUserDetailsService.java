//package pl.medos.cmmsApi.security;
//
//
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import pl.medos.cmmsApi.repository.UserRepository;
//
//import pl.medos.cmmsApi.repository.entity.UserEntity;
//import java.util.stream.Collectors;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private UserRepository userRepository;
//
//    public CustomUserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
//
//        UserEntity userEntity = userRepository.findByEmail(usernameOrEmail);
//        if(userEntity != null){
//            return new org.springframework.security.core.userdetails.User(userEntity.getEmail()
//                    , userEntity.getPassword(),
//                    userEntity.getRoleEntities().stream()
//                            .map((role) -> new SimpleGrantedAuthority(role.getName()))
//                            .collect(Collectors.toList()));
//        }else {
//            throw new UsernameNotFoundException("Invalid email or password");
//        }
//    }
//}
