package pl.medos.cmmsApi.controllers;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.HardwareNotFoundException;
import pl.medos.cmmsApi.model.Hardware;
import pl.medos.cmmsApi.service.HardwareService;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/hardwares")
public class WebHardwareController {

    private static final Logger LOGGER = Logger.getLogger(WebHardwareController.class.getName());
    private HardwareService hardwareService;

    public WebHardwareController(HardwareService hardwareService) {
        this.hardwareService = hardwareService;
    }

    @GetMapping(value = "/list")
    public String listViewAll(Model model) {
        LOGGER.info("listViewAll()");
        List<Hardware> hardwares = hardwareService.listAll();
        model.addAttribute("hardwares", hardwares);
        LOGGER.info("listViewAll(...)");
        return "list-hardware";
    }
    @GetMapping
    public String pageing(@RequestParam(value = "pageNo") int pageNo, Model model){
        LOGGER.info("pageing()");
        int size = 5;
        Page<Hardware> hardwares = hardwareService.pagesHardware(pageNo, size);
        model.addAttribute("hardwares", hardwares);
        LOGGER.info("pageing(...)");
        return "page-hardware";
    }

    @GetMapping("/create")
    public String createView(Model model) {
        LOGGER.info("createView()");
        model.addAttribute("hardware", new Hardware());
        LOGGER.info("createView(...)");
        return "create-Hardware";
    }

    @PostMapping("/create")
    public String createHardware(
            @ModelAttribute(name = "hardware") Hardware hardware) {
        LOGGER.info("createHardware()");
        Hardware savedHardware = hardwareService.create(hardware);
        LOGGER.info("createHardware(...)" + savedHardware);
        return "redirect:/hardwares";
    }

    @GetMapping(value = "/findHardware")
    public String findHardware(@PathVariable(name = "id") Long id) throws HardwareNotFoundException {
        LOGGER.info("findHardware(" + id + ")");
        Hardware findHardware = hardwareService.read(id);
        LOGGER.info("findHardware(...)" + findHardware);
        return "read-hardware";
    }

    @GetMapping("/update/{id}")
    public String updateView(@PathVariable(name = "id") Long id, Model model) throws HardwareNotFoundException {
        LOGGER.info("updateView(" + id + ")");
        Hardware hardware = hardwareService.read(id);
        model.addAttribute("hardware", hardware);
        LOGGER.info("updateView(...)");
        return "update-hardware";
    }

    @PostMapping("/update")
    public String updateHardware(@ModelAttribute(name = "hardware") Hardware hardware) throws HardwareNotFoundException {
        LOGGER.info("updateHardware()");
        Hardware updatedHardware = hardwareService.update(hardware);
        LOGGER.info("updateHardware(...)");
        return "redirect:/hardwares";
    }

    @GetMapping("/delete/{id}")
    public void deleteHardware(@PathVariable(name = "id") Long id) {
        LOGGER.info("deleteHardware(" + id + ")");
        hardwareService.delete(id);
        LOGGER.info("deleteHardware(...)");
    }
}
