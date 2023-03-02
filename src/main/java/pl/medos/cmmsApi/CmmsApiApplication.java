package pl.medos.cmmsApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class CmmsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmmsApiApplication.class, args);
	}

}
