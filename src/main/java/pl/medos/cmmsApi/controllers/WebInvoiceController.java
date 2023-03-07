package pl.medos.cmmsApi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.InvoiceNotFoundException;
import pl.medos.cmmsApi.model.Invoice;
import pl.medos.cmmsApi.model.Supplier;
import pl.medos.cmmsApi.service.impl.InvoiceServiceImpl;
import pl.medos.cmmsApi.service.impl.SupplierServiceImpl;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/invoices")
public class WebInvoiceController {

    private static final Logger LOGGER = Logger.getLogger(WebInvoiceController.class.getName());

    private InvoiceServiceImpl invoiceServiceImpl;
    private SupplierServiceImpl supplierServiceImpl;

    public WebInvoiceController(InvoiceServiceImpl invoiceServiceImpl, SupplierServiceImpl supplierServiceImpl) {
        this.invoiceServiceImpl = invoiceServiceImpl;
        this.supplierServiceImpl = supplierServiceImpl;
    }

    @GetMapping
    public String listView(ModelMap modelMap) {
        LOGGER.info("listView()");
        List<Invoice> invoices = invoiceServiceImpl.findAllInvoices();
        modelMap.addAttribute("invoices", invoices);
        List<Supplier> suppliers = supplierServiceImpl.list();
        modelMap.addAttribute("supplier", suppliers);
        LOGGER.info("listView(...)");
        return "list-invoice.html";
    }

    @GetMapping(value = "/create")
    public String createView(ModelMap modelMap) {
        LOGGER.info("createView()");
        modelMap.addAttribute("invoice", new Invoice());
        List<Supplier> suppliers = supplierServiceImpl.list();
        modelMap.addAttribute("suppliers", suppliers);
        LOGGER.info("createView(...)");
        return "create-invoice.html";
    }

    @PostMapping(value = "/create")
    public String create(@ModelAttribute(name = "invoice") Invoice invoice) {
        LOGGER.info("create()");
        Invoice savedInvoice = invoiceServiceImpl.createInvoice(invoice);
        LOGGER.info("create(...)" + savedInvoice);
        return "redirect:/invoices";
    }

    @GetMapping(value = "/read/{id}")
    public String read(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws InvoiceNotFoundException {
        LOGGER.info("read(" + id + ")");
        Invoice invoiceModel = invoiceServiceImpl.findInvoiceById(id);
        modelMap.addAttribute("invoice", invoiceModel);
        LOGGER.info("read(...)" + invoiceModel);
        return "read-invoice.html";
    }

    @GetMapping(value = "/update/{id}")
    public String updateView(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws InvoiceNotFoundException {
        LOGGER.info("update()" + id);
        Invoice invoice = invoiceServiceImpl.findInvoiceById(id);
        modelMap.addAttribute("invoice", invoice);
        List<Supplier> suppliers = supplierServiceImpl.list();
        modelMap.addAttribute("suppliers", suppliers);
        LOGGER.info("update(...)");
        return "create-invoice.html";
    }

    @PutMapping(value = "/update")
    public String update(
            @ModelAttribute(name = "invoice") Invoice invoice) throws InvoiceNotFoundException {
        LOGGER.info("update()" + invoice);
        Invoice updatedInvoice = invoiceServiceImpl.updateInvoice(invoice);
        LOGGER.info("update(...)" + updatedInvoice);
        return "redirect:/invoices";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable(name = "id") Long id) {
        LOGGER.info("delete(" + id + ")");
        invoiceServiceImpl.deleteInvoice(id);
        LOGGER.info("delete(...)");
        return "redirect:/invoices";
    }
}
