package pl.medos.cmmsApi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import pl.medos.cmmsApi.enums.Device;
import pl.medos.cmmsApi.enums.Permission;
import pl.medos.cmmsApi.enums.Status;
import pl.medos.cmmsApi.repository.HardwareRepository;
import pl.medos.cmmsApi.repository.entity.HardwareEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class CommandLineAppStartupRunner implements CommandLineRunner {
    @Autowired
    ImageLoader imageLoader;

    private static final String UPLOAD_DIR = "/home/images";
    @Autowired
    HardwareRepository hardwareRepository;

    @Value("${admins}")
    private String[] admins;


    @Override
    public void run(String... args) throws Exception {

        List<String> ipList = Arrays.asList(admins);
        ipList.stream().forEach(ipAddres -> {

            Optional<HardwareEntity> hardwareByIp = hardwareRepository.findHardwareByIp(ipAddres);

            if (!hardwareByIp.isPresent()) {
                log.info("Creating admin account()");

                HardwareEntity hardware = new HardwareEntity();
                hardware.setIpAddress(ipAddres);
                hardware.setName("AdminPC");
                hardware.setStatus(Status.W_UŻYCIU);
                hardware.setType(Device.KOMPUTER_STACJONARNY);
                Permission permission = Permission.ADMIN;

                hardware.setPermission(permission);
                HardwareEntity hardwareEntity = hardwareRepository.save(hardware);
                log.info("Created: " + hardwareEntity.getIpAddress() + " / " + hardwareEntity.getPermission());

            } else {
                log.info("Admin allready exist...");

                HardwareEntity hardwareEntity = hardwareByIp.orElseThrow();

                if ((hardwareEntity.getPermission() == null)) {
                    log.info("Add admin permission to existing profile.");
                    Permission permission = Permission.ADMIN;
                    hardwareEntity.setPermission(permission);
                    HardwareEntity accessEntity = hardwareRepository.save(hardwareEntity);
                }
            }
        });


//        String relativePath = "static/images/default.jpg";

//        try {
//            ClassPathResource resource = new ClassPathResource(relativePath);
//            Path filePath = resource.getFile().toPath();
//            log.info("upoloadPath " + Paths.get(UPLOAD_DIR));
//            log.info("myfilePath " + filePath);
//            Files.copy(filePath, Paths.get(UPLOAD_DIR));
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

}
