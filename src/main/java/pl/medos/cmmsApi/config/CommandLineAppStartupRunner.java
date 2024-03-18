package pl.medos.cmmsApi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.medos.cmmsApi.enums.Device;
import pl.medos.cmmsApi.enums.Permission;
import pl.medos.cmmsApi.enums.Status;
import pl.medos.cmmsApi.repository.HardwareRepository;
import pl.medos.cmmsApi.repository.entity.HardwareEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private static final Logger LOGGER = Logger.getLogger(CommandLineAppStartupRunner.class.getName());
    private ImageLoader imageLoader;
    private HardwareRepository hardwareRepository;

    @Value("${admins}")
    private String[] admins;


    public CommandLineAppStartupRunner(ImageLoader imageLoader, HardwareRepository hardwareRepository) {
        this.imageLoader = imageLoader;
        this.hardwareRepository = hardwareRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        List<String> ipList = Arrays.asList(admins);
        ipList.stream().forEach(ipAddres -> {
            Optional<HardwareEntity> hardwareByIp = hardwareRepository.findHardwareByIp(ipAddres);
            if (!hardwareByIp.isPresent()) {
                LOGGER.info("Creating admin account()");
                HardwareEntity hardware = new HardwareEntity();
                hardware.setIpAddress(ipAddres);
                hardware.setName("AdminPC");
                hardware.setStatus(Status.W_UŻYCIU);
                hardware.setType(Device.KOMPUTER_STACJONARNY);
                Permission permission = Permission.ADMIN;
                hardware.setPermission(permission);
                HardwareEntity hardwareEntity = hardwareRepository.save(hardware);
                LOGGER.info("Created: " + hardwareEntity.getIpAddress() + " / " + hardwareEntity.getPermission());
            } else {
                LOGGER.info("Admin allready exist...");
                HardwareEntity hardwareEntity = hardwareByIp.orElseThrow();
                if ((hardwareEntity.getPermission() == null)) {
                    LOGGER.info("Add admin permission to existing profile.");
                    Permission permission = Permission.ADMIN;
                    hardwareEntity.setPermission(permission);
                    HardwareEntity accessEntity = hardwareRepository.save(hardwareEntity);
                }
            }
        });
    }
}
