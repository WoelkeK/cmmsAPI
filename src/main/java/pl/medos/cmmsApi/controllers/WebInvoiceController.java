package pl.medos.cmmsApi.controllers;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.InvoiceNotFoundException;
import pl.medos.cmmsApi.model.Invoice;
import pl.medos.cmmsApi.model.Part;
import pl.medos.cmmsApi.model.Resource;
import pl.medos.cmmsApi.model.Supplier;
import pl.medos.cmmsApi.repository.ResourceRepository;
import pl.medos.cmmsApi.service.InvoiceService;
import pl.medos.cmmsApi.service.ResourceService;
import pl.medos.cmmsApi.service.SupplierService;
import pl.medos.cmmsApi.service.impl.InvoiceServiceImpl;
import pl.medos.cmmsApi.service.impl.SupplierServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/invoices")
@SessionAttributes(names = {"invoices", "suppliers", "resources"})
public class WebInvoiceController {

    private static final Logger LOGGER = Logger.getLogger(WebInvoiceController.class.getName());

    private InvoiceService invoiceService;
    private SupplierService supplierService;
    private ResourceService resourceService;
    private final ResourceRepository resourceRepository;


    public WebInvoiceController(InvoiceService invoiceService, SupplierService supplierService, ResourceService resourceService,
                                ResourceRepository resourceRepository) {
        this.invoiceService = invoiceService;
        this.supplierService = supplierService;
        this.resourceService = resourceService;
        this.resourceRepository = resourceRepository;
    }

    @GetMapping
    public String listView(Model modelMap) {
        LOGGER.info("listView()");
        List<Invoice> invoices = invoiceService.findAllInvoices();
        modelMap.addAttribute("invoices", invoices);
        List<Supplier> suppliers = supplierService.list();
        modelMap.addAttribute("supplier", suppliers);
        List<Resource> resources = resourceService.findAllResources();
        modelMap.addAttribute("resources", resources);
        return "list-invoice.html";
    }

    @GetMapping(value = "/create")
    public String createView(ModelMap modelMap) {
        LOGGER.info("createView()");
        modelMap.addAttribute("invoice", new Invoice());
        List<Supplier> suppliers = supplierService.list();
        modelMap.addAttribute("suppliers", suppliers);
        LOGGER.info("createView(...)");
        return "create-invoice.html";
    }

    @PostMapping(value = "/create")
    public String create(@ModelAttribute(name = "invoice") Invoice invoice) {
        LOGGER.info("create()");
        Invoice savedInvoice = invoiceService.createInvoice(invoice);
        LOGGER.info("create(...)" + savedInvoice);
        return "redirect:/invoices";
    }

    @GetMapping(value = "/read/{id}")
    public String read(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws InvoiceNotFoundException {
        LOGGER.info("read(" + id + ")");
        Invoice invoiceModel = invoiceService.findInvoiceById(id);
        modelMap.addAttribute("invoice", invoiceModel);
        LOGGER.info("read(...)" + invoiceModel);
        return "read-invoice.html";
    }

    @GetMapping(value = "/update/{id}")
    public String updateView(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws InvoiceNotFoundException {
        LOGGER.info("update()" + id);
        Invoice invoice = invoiceService.findInvoiceById(id);
        modelMap.addAttribute("invoice", invoice);
        List<Supplier> suppliers = supplierService.list();
        modelMap.addAttribute("suppliers", suppliers);
        LOGGER.info("update(...)");
        return "create-invoice.html";
    }

    @PutMapping(value = "/update")
    public String update(
            @ModelAttribute(name = "invoice") Invoice invoice) throws InvoiceNotFoundException {
        LOGGER.info("update()" + invoice);
        Invoice updatedInvoice = invoiceService.updateInvoice(invoice);
        LOGGER.info("update(...)" + updatedInvoice);
        return "redirect:/invoices";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable(name = "id") Long id) {
        LOGGER.info("delete(" + id + ")");
        invoiceService.deleteInvoice(id);
        LOGGER.info("delete(...)");
        return "redirect:/invoices";
    }
}
