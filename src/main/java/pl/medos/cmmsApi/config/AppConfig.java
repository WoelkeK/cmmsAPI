package pl.medos.cmmsApi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.servlet.MultipartConfigElement;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.unit.DataSize;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.UrlTemplateResolver;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }


    @Bean
    public ObjectMapper objectMapper() {ObjectMapper mapper = JsonMapper.builder().findAndAddModules().build();
        return mapper;}



}
