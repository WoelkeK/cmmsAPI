//package pl.medos.cmmsApi.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//@EnableWebSecurity
//@Configuration
//public class SecurityConfig {
//
//    @Autowired
//    private UserDetailsService uds;
//
//    @Autowired
//    private BCryptPasswordEncoder encoder;
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http.authorizeHttpRequests()
//                .requestMatchers("/home", "/login.html", "/register", "/saveUser", "/javainuse-openapi/**").permitAll()
//                .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/swagger-resources/**", "/swagger-resources", "/v3/api-docs/**", "/proxy/**").permitAll()
//                .requestMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**").permitAll()
//                .requestMatchers("/welcome").authenticated()
//                .requestMatchers("/admin").hasAuthority("Admin")
//                .requestMatchers("/mgr").hasAuthority("Manager")
//                .requestMatchers("/emp").hasAuthority("Employee")
//                .requestMatchers("/hr").hasAuthority("HR")
//                .requestMatchers("/common").hasAnyAuthority("Employeee,Manager,Admin")
//                .anyRequest().permitAll()
//
//                .and()
//                .formLogin()
//                .loginPage("/login.html")
//                .loginProcessingUrl("/login")
//                .defaultSuccessUrl("/welcome", true)
//                .failureUrl("/login?error=true")
//                .permitAll()
//
//                .and()
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
//
//                .and()
//                .exceptionHandling()
//                .accessDeniedPage("/accessDenied")
//
//                .and()
//                .authenticationProvider(authenticationProvider());
//
//        return http.build();
//
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(uds);
//        authenticationProvider.setPasswordEncoder(encoder);
//        return authenticationProvider;
//    }
//}