package pl.medos.cmmsApi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.medos.cmmsApi.enums.Device;
import pl.medos.cmmsApi.enums.Permission;
import pl.medos.cmmsApi.enums.Status;
import pl.medos.cmmsApi.model.Hardware;
import pl.medos.cmmsApi.model.Type;
import pl.medos.cmmsApi.repository.HardwareRepository;
import pl.medos.cmmsApi.repository.entity.HardwareEntity;
import pl.medos.cmmsApi.service.HardwareService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class CommandLineAppStartupRunner implements CommandLineRunner {
    @Autowired
    HardwareRepository hardwareRepository;

    @Value( "${admins}" )
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
            }
        });
    }
}
