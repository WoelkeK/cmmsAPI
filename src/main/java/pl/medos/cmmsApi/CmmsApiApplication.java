package pl.medos.cmmsApi;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.CrossOrigin;
@SpringBootApplication
@EnableScheduling
public class  CmmsApiApplication{

    public static void main(String[] args) {
        SpringApplication.run(CmmsApiApplication.class, args);
    }

}
