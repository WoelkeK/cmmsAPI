package pl.medos.cmmsApi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.service.ImportService;

import java.util.logging.Logger;

@Controller
@RequestMapping("/imports")
public class WebImportController {

    private static final Logger LOGGER = Logger.getLogger(WebImportController.class.getName());

    private ImportService importService;

    public WebImportController(ImportService importService) {
        this.importService = importService;
    }

    @GetMapping(value = "/persons")
    public String createView(Model model) {
        LOGGER.info("import person)");
        model.addAttribute("import", new Department());
        return "import.html";
    }

    @PostMapping(value = "/persons")
    public String create(){
        LOGGER.info("persons()");
        LOGGER.info("persons(...)");
        return "redirect:/imports";
    }
}
