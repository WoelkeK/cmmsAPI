package pl.medos.cmmsApi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.ResourceNotFoundException;
import pl.medos.cmmsApi.model.Invoice;
import pl.medos.cmmsApi.model.Resource;
import pl.medos.cmmsApi.repository.InvoiceRepository;
import pl.medos.cmmsApi.service.InvoiceService;
import pl.medos.cmmsApi.service.ResourceService;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/resources")
public class WebResourceController {

    private static final Logger LOGGER = Logger.getLogger(WebResourceController.class.getName());

    private ResourceService resourceService;
    private InvoiceService invoiceService;



    public WebResourceController(ResourceService resourceService, InvoiceService invoiceService) {
        this.resourceService = resourceService;
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public String listView(ModelMap modelMap) {
        LOGGER.info("listView()");
        List<Resource> resources = resourceService.list();
        modelMap.addAttribute("resources", resources);
        LOGGER.info("listView(...)");
        return "list-resource.html";
    }

    @GetMapping(value = "/create")
    public String createView(ModelMap modelMap) {
        LOGGER.info("createView()");
        modelMap.addAttribute("resources", new Resource());
        LOGGER.info("createView(...)");
        return "create-resource.html";
    }

    @PostMapping(value = "/create")
    public String create(@ModelAttribute(name = "resources") Resource resource) {
        LOGGER.info("create()");
        Resource savedResource = resourceService.create(resource);
        LOGGER.info("create(...)" + savedResource);
        return "redirect:/resources";
    }

    @GetMapping(value = "/read/{id}")
    public String read(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws ResourceNotFoundException {
        LOGGER.info("read(" + id + ")");
        Resource resourceModel = resourceService.read(id);
        modelMap.addAttribute("resources", resourceModel);
        LOGGER.info("read(...)" + resourceModel);
        return "read-resource.html";
    }

    @GetMapping(value = "/update/{id}")
    public String updateView(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws ResourceNotFoundException {
        LOGGER.info("update()" + id);
        Resource resource = resourceService.read(id);
        modelMap.addAttribute("resources", resource);
        LOGGER.info("update(...)");
        return "update-resource.html";
    }

    @PutMapping(value = "/update")
    public String update(
            @ModelAttribute(name = "resources") Resource resource) throws ResourceNotFoundException {
        LOGGER.info("update()" + resource);
        Resource updatedResource = resourceService.update(resource);
        LOGGER.info("update(...)" + updatedResource);
        return "redirect:/resources";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(Long id) {
        LOGGER.info("delete(" + id + ")");
        resourceService.delete(id);
        LOGGER.info("delete(...)");
        return "Response 200: Record: " + id + " deleted";
    }
}
