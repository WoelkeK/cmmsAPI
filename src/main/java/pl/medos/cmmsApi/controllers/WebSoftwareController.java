package pl.medos.cmmsApi.controllers;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.HardwareNotFoundException;
import pl.medos.cmmsApi.exception.SoftwareNotFoundException;
import pl.medos.cmmsApi.model.Hardware;
import pl.medos.cmmsApi.model.Software;
import pl.medos.cmmsApi.service.*;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/softwares")
@SessionAttributes(names = {"softwares", "hardwares"})
public class WebSoftwareController {

    private static final Logger LOGGER = Logger.getLogger(WebSoftwareController.class.getName());

    private SoftwareService softwareService;

    private HardwareService hardwareService;

    public WebSoftwareController(SoftwareService softwareService, HardwareService hardwareService) {
        this.softwareService = softwareService;
        this.hardwareService = hardwareService;
    }

    @GetMapping("/listAll")
    public String listViewAll(Model model) {
        LOGGER.info("listViewAll()");
        List<Software> softwares = softwareService.listAllSoftware();
        model.addAttribute("softwares", softwares);
        LOGGER.info("listViewAll(...)");
        return "list-software";
    }

    @GetMapping("")
    public String listView(Model model) throws IOException {
        LOGGER.info("listView()");
        List<Hardware> hardwares = hardwareService.listAll();
        model.addAttribute("hardwares", hardwares);
        return pagesSoftware(1, "id", "desc", model);
    }

    @GetMapping("/page/{pageNo}")
    public String pagesSoftware(@PathVariable(name = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model){
        LOGGER.info("pageSoftware() " + pageNo + " " + sortField + " " +  sortDir);
        int size = 5;
        Page softwarePages = softwareService.pageSoftware(pageNo, size, sortField, sortDir);
        List softwares = softwarePages.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", softwarePages.getTotalPages());
        model.addAttribute("totalItems", softwarePages.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("softwares", softwares);
        LOGGER.info("pageSoftware(...)");
        return "hardware-front";

    }

    @GetMapping("/create")
    public String createView(Model model) {
        LOGGER.info("createView()");
        model.addAttribute("software", new Software());
        LOGGER.info("createView(...)");
        return "create-software";
    }

    @PostMapping("/create")
    public String createHardware(
            @ModelAttribute(name = "software") Software software) throws HardwareNotFoundException {
        LOGGER.info("createSoftware()" + software);
        Software savedSoftware = softwareService.create(software);
        LOGGER.info("createSoftware(...)" + savedSoftware);
        return "redirect:/softwares";
    }

    @GetMapping(value = "/read/{id}")
    public String findSoftware(@PathVariable(name = "id") Long id,
                               Model model) throws SoftwareNotFoundException {
        LOGGER.info("findSoftware(" + id + ")");
        Software softwareById = softwareService.read(id);
        model.addAttribute("software", softwareById);
        LOGGER.info("findSoftware(...)");
        return "view-software";
    }

    @GetMapping("/update/{id}")
    public String updateView(@PathVariable(name = "id") Long id, Model model) throws SoftwareNotFoundException {
        LOGGER.info("updateView(" + id + ")");
        Software software = softwareService.read(id);
        model.addAttribute("software", software);
        LOGGER.info("updateView(...)");
        return "update-software";
    }

    @PostMapping("/update")
    public String updateSoftware(
            @ModelAttribute(name = "software") Software software, Long id) throws SoftwareNotFoundException {
        LOGGER.info("updateSoftware()");
        softwareService.update(software, id);
        LOGGER.info("updateSoftware(...)");
        return "redirect:/softwares";
    }

    @GetMapping("/delete/{id}")
    public String deleteHardware(@PathVariable(name = "id") Long id) {
        LOGGER.info("deleteSoftware(" + id + ")");
        softwareService.delete(id);
        LOGGER.info("deleteSoftware(...)");
        return "redirect:/softwares";
    }

    @GetMapping(value = "/search/query")
    public String searchSoftwareByQuery(@RequestParam(value = "query") String query,
                                       Model model) {
        LOGGER.info("search()");
        List<Software> softwares = softwareService.findHardwaresByQuery(query);
        model.addAttribute("softwares", softwares);
        return "list-software";
    }
}
