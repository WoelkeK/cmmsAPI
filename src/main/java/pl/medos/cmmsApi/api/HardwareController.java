package pl.medos.cmmsApi.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.HardwareNotFoundException;
import pl.medos.cmmsApi.model.Hardware;
import pl.medos.cmmsApi.service.HardwareService;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/hardwares")
@Slf4j
public class HardwareController {

    private HardwareService hardwareService;

    public HardwareController(HardwareService hardwareService) {
        this.hardwareService = hardwareService;
    }

    @GetMapping("/list")
    public List<Hardware> listAllHardware() {
        log.debug("listAllHardwrae()");
        List<Hardware> hardwares = hardwareService.listAll();
        log.debug("listAllHardware()");
        return hardwares;
    }

    @GetMapping("/read/{id}")
    public Hardware findHardwareById(@PathVariable(name = "id") Long id) throws HardwareNotFoundException {
        log.debug("readHardwrae()");
        Hardware readedHardware = hardwareService.read(id);
        log.debug("readHardwrae(...)");
        return readedHardware;
    }

    @PostMapping("/create")
    public Hardware createHardware(@ModelAttribute(name = "hardware") Hardware hardware) {
        log.debug("createHardwrae()");
        Hardware createdHardware = createHardware(hardware);
        log.debug("createHardwrae(...)");
        return createdHardware;
    }

    @PutMapping("/update/{id}")
    public Hardware update(@PathVariable(name = "id") Long id,
                           @ModelAttribute(name = "hardware") Hardware hardware) throws HardwareNotFoundException {
        log.debug("updateHardwrae()");
        Hardware savedHardware = hardwareService.update(hardware);
        log.debug("updateHardwrae(...)");
        return savedHardware;
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        log.debug("deleteHardwrae()");
        hardwareService.delete(id);
        log.debug("deleteHardwrae(...)");
    }
}
