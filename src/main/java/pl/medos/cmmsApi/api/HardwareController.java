package pl.medos.cmmsApi.api;

import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.HardwareNotFoundException;
import pl.medos.cmmsApi.model.Hardware;
import pl.medos.cmmsApi.service.HardwareService;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/hardwares")
public class HardwareController {

    private static final Logger LOGGER = Logger.getLogger(HardwareController.class.getName());

    private HardwareService hardwareService;

    public HardwareController(HardwareService hardwareService) {
        this.hardwareService = hardwareService;
    }

    @GetMapping("/list")
    public List<Hardware> listAllHardware() {
        LOGGER.info("listAllHardwrae()");
        List<Hardware> hardwares = hardwareService.listAll();
        LOGGER.info("listAllHardware()");
        return hardwares;
    }

    @GetMapping("/read/{id}")
    public Hardware findHardwareById(@PathVariable(name = "id") Long id) throws HardwareNotFoundException {
        LOGGER.info("readHardwrae()");
        Hardware readedHardware = hardwareService.read(id);
        LOGGER.info("readHardwrae(...)");
        return readedHardware;
    }

    @PostMapping("/create")
    public Hardware createHardware(@ModelAttribute(name = "hardware") Hardware hardware) {
        LOGGER.info("createHardwrae()");
        Hardware createdHardware = createHardware(hardware);
        LOGGER.info("createHardwrae(...)");
        return createdHardware;
    }

    @PutMapping("/update/{id}")
    public Hardware update(@PathVariable(name = "id") Long id,
                           @ModelAttribute(name = "hardware") Hardware hardware) throws HardwareNotFoundException {
        LOGGER.info("updateHardwrae()");
        Hardware savedHardware = hardwareService.update(hardware);
        LOGGER.info("updateHardwrae(...)");
        return savedHardware;
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        LOGGER.info("deleteHardwrae()");
        hardwareService.delete(id);
        LOGGER.info("deleteHardwrae(...)");
    }
}
