package pl.medos.cmmsApi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.ResourceNotFoundException;
import pl.medos.cmmsApi.model.Invoice;
import pl.medos.cmmsApi.model.Resource;
import pl.medos.cmmsApi.repository.InvoiceRepository;
import pl.medos.cmmsApi.service.impl.InvoiceServiceImpl;
import pl.medos.cmmsApi.service.impl.ResourceServiceImpl;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/resources")
public class WebResourceController {

    private static final Logger LOGGER = Logger.getLogger(WebResourceController.class.getName());

    private ResourceServiceImpl resourceServiceImpl;
    private InvoiceServiceImpl invoiceServiceImpl;
    private final InvoiceRepository invoiceRepository;


    public WebResourceController(ResourceServiceImpl resourceServiceImpl, InvoiceServiceImpl invoiceServiceImpl,
                                 InvoiceRepository invoiceRepository) {
        this.resourceServiceImpl = resourceServiceImpl;
        this.invoiceServiceImpl = invoiceServiceImpl;
        this.invoiceRepository = invoiceRepository;
    }

    @GetMapping
    public String listView(ModelMap modelMap) {
        LOGGER.info("listView()");
        List<Resource> resources = resourceServiceImpl.findAllResources();
        modelMap.addAttribute("resources", resources);
        List<Invoice> invoices = invoiceServiceImpl.findAllInvoices();
        modelMap.addAttribute("invoices", invoices);
        LOGGER.info("listView(...)");
        return "list-resource.html";
    }

    @GetMapping(value = "/create")
    public String createView(ModelMap modelMap) {
        LOGGER.info("createView()");
        modelMap.addAttribute("resource", new Resource());
        List<Invoice> invoices = invoiceServiceImpl.findAllInvoices();
        modelMap.addAttribute("invoices", invoices);
        LOGGER.info("createView(...)");
        return "create-resource.html";
    }

    @PostMapping(value = "/create")
    public String create(@ModelAttribute(name = "resources") Resource resource) {
        LOGGER.info("create()");
        Resource savedResource = resourceServiceImpl.createResource(resource);
        LOGGER.info("create(...)" + savedResource);
        return "redirect:/resources";
    }

    @GetMapping(value = "/read/{id}")
    public String read(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws ResourceNotFoundException {
        LOGGER.info("read(" + id + ")");
        Resource resourceModel = resourceServiceImpl.findResourceById(id);
        modelMap.addAttribute("resource", resourceModel);
        LOGGER.info("read(...)" + resourceModel);
        return "read-resource.html";
    }

    @GetMapping(value = "/update/{id}")
    public String updateView(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws ResourceNotFoundException {
        LOGGER.info("update()" + id);
        Resource resource = resourceServiceImpl.findResourceById(id);
        modelMap.addAttribute("resource", resource);
        List<Invoice> invoices = invoiceServiceImpl.findAllInvoices();
        modelMap.addAttribute("invoices", invoices);
        LOGGER.info("update(...)");
        return "create-resource.html";
    }

    @PutMapping(value = "/update")
    public String update(
            @ModelAttribute(name = "resources") Resource resource) throws ResourceNotFoundException {
        LOGGER.info("update()" + resource);
        Resource updatedResource = resourceServiceImpl.updateResource(resource);
        LOGGER.info("update(...)" + updatedResource);
        return "redirect:/resources";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable(name = "id") Long id) {
        LOGGER.info("delete(" + id + ")");
        resourceServiceImpl.deleteResource(id);
        LOGGER.info("delete(...)");
        return "redirect:/resources";
    }
}
