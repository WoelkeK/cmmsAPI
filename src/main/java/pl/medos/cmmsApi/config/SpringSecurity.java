package pl.medos.cmmsApi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // configure SecurityFilterChain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/javainuse-openapi/**").permitAll()
                .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/swagger-resources/**", "/swagger-resources", "/v3/api-docs/**", "/proxy/**").permitAll()
                .requestMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/register/**").permitAll()
                .requestMatchers("/images/**").permitAll()
                .requestMatchers("/cron/**").permitAll()
                .requestMatchers("/api/**").permitAll()
                .requestMatchers("/info/**").permitAll()
                .requestMatchers("/awizacje/**").permitAll()
                .requestMatchers("/softwares/**").permitAll()
                .requestMatchers("/dashboards/**").permitAll()
//                .requestMatchers("/hardwares/**").permitAll()
                .requestMatchers("/hardwares/**").hasRole("ADMIN")
                .requestMatchers("/employees/**").hasRole("ADMIN")
                .requestMatchers("/index/**").hasRole("ADMIN")
                .requestMatchers("/export/**").hasRole("ADMIN")
                .requestMatchers("/imports/**").hasRole("ADMIN")
                .requestMatchers("/welcome/**").hasRole("ADMIN")
                .requestMatchers("/machines/**").hasRole("ADMIN")
                .requestMatchers("/employees/**").hasRole("ADMIN")
                .requestMatchers("/engineers/**").hasRole("ADMIN")
                .requestMatchers("/departments/**").hasRole("ADMIN")
                .requestMatchers("/invoices/**").hasRole("ADMIN")
                .requestMatchers("/jobs/**").hasRole("ADMIN")
                .requestMatchers("/suppliers/**").hasRole("ADMIN")
                .requestMatchers("/resources/**").hasRole("ADMIN")
                .requestMatchers("/costs/**").hasRole("ADMIN")
                .requestMatchers("/parts/**").hasRole("ADMIN")
                .requestMatchers("/schedules/**").hasRole("ADMIN")
//                .requestMatchers("/users").hasRole("USER")
                .and()
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/index", true)
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()

                );
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
