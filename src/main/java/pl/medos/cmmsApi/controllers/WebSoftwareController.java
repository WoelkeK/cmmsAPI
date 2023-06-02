package pl.medos.cmmsApi.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.dto.EmployeesImportDto;
import pl.medos.cmmsApi.exception.HardwareNotFoundException;
import pl.medos.cmmsApi.exception.SoftwareNotFoundException;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.model.Hardware;
import pl.medos.cmmsApi.model.Software;
import pl.medos.cmmsApi.service.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/softwares")
@SessionAttributes(names = {"softwares"})
public class WebSoftwareController {

    private static final Logger LOGGER = Logger.getLogger(WebSoftwareController.class.getName());

    private SoftwareService softwareService;

    public WebSoftwareController(SoftwareService softwareService) {
        this.softwareService = softwareService;
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
        return pagesSoftware(1, "name", "desc", model);
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
        return "list-software";

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
            @ModelAttribute(name = "software") Software software) {
        LOGGER.info("createHardware()" + software);
        Software savedSoftware = softwareService.create(software);
        LOGGER.info("createHardware(...)" + savedSoftware);
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
