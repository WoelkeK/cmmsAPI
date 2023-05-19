package pl.medos.cmmsApi.service.impl;

import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.model.Hardware;
import pl.medos.cmmsApi.repository.HardwareRepository;
import pl.medos.cmmsApi.service.HardwareService;

import java.util.logging.Logger;

@Service
public class HardwareServiceImpl implements HardwareService {

    private static final Logger LOGGER = Logger.getLogger(HardwareServiceImpl.class.getName());

    private HardwareRepository hardwareRepository;

    public HardwareServiceImpl(HardwareRepository hardwareRepository) {
        this.hardwareRepository = hardwareRepository;
    }

    @Override
    public void create(Hardware hardware) {
        LOGGER.info("createHardware()");


        LOGGER.info("createHardware(...)");

    }

    @Override
    public void read(Long id) {
        LOGGER.info("readHardware()");
        LOGGER.info("readHardware(...)");

    }

    @Override
    public void update(Hardware hardware) {
        LOGGER.info("updateHardware()");
        LOGGER.info("updateHardware(...)");

    }

    @Override
    public void delete(Long id) {
        LOGGER.info("deleteHardware()");
        LOGGER.info("deleteHardware(...)");

    }
}
