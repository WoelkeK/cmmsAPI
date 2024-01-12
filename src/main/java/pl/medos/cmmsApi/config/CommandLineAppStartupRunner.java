package pl.medos.cmmsApi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.medos.cmmsApi.enums.Permission;
import pl.medos.cmmsApi.model.Hardware;
import pl.medos.cmmsApi.repository.HardwareRepository;
import pl.medos.cmmsApi.repository.entity.HardwareEntity;
import pl.medos.cmmsApi.service.HardwareService;

import java.util.Optional;

@Component
@Slf4j
public class CommandLineAppStartupRunner implements CommandLineRunner {
    @Autowired
    HardwareRepository hardwareRepository;

    @Override
    public void run(String... args) throws Exception {

        Optional<HardwareEntity> hardwareByIp = hardwareRepository.findHardwareByIp("0:0:0:0:0:0:0:1");
        if (!hardwareByIp.isPresent()) {
            log.info("Creating admin account()");

            HardwareEntity hardware = new HardwareEntity();
            hardware.setIpAddress("0:0:0:0:0:0:0:1");
            Permission permission = Permission.ADMIN;
            hardware.setPermission(permission);
            HardwareEntity hardwareEntity = hardwareRepository.save(hardware);
            log.info("Created: " + hardwareEntity.getIpAddress() + " / " + hardwareEntity.getPermission());
        } else {

            log.info("Admin allready exist...");
        }
    }
}
