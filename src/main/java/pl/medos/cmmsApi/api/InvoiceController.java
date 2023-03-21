package pl.medos.cmmsApi.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.InvoiceNotFoundException;
import pl.medos.cmmsApi.model.Invoice;
import pl.medos.cmmsApi.service.InvoiceService;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class InvoiceController {

    private static final Logger LOGGER = Logger.getLogger(InvoiceController.class.getName());

    private final InvoiceService invoiceService;

    @GetMapping("/invoices")
    public void getAllInvoices() {
        LOGGER.info("getAllInvoices()");
        List<Invoice> invoices = invoiceService.findAllInvoices();
        LOGGER.info("getAllInvoices(...)");
    }

    @PostMapping("/invoice")
    public void createSupplier() {
        LOGGER.info("createInvoice)");
        Invoice invoice = invoiceService.createInvoice();
        LOGGER.info("createInvoice(...)"+ invoice);
    }

    @GetMapping("/invoice/{id}")
    public void findInvoiceById(@PathVariable(name = "id") Long id) throws InvoiceNotFoundException {
        LOGGER.info("findInvoiceById()");
        Invoice invoiceById = invoiceService.findInvoiceById(id);
        LOGGER.info("findInvoiceById(...)" + invoiceById);
    }

    @PutMapping("/invoice/{id}")
    public void updateInvoice(@ModelAttribute Invoice invoice, @PathVariable(name = "id") Long id) {
        LOGGER.info("updateInvoice()" + id);
        Invoice updatedInvoice = invoiceService.updateInvoice(invoice, id);
        LOGGER.info("updateInvoice(...)");
    }

    @DeleteMapping("/invoice/{id}")
    public void deleteInvoice(@PathVariable(name = "id") Long id) {
        LOGGER.info("deleteSupplier()");
        invoiceService.deleteInvoice(id);
        LOGGER.info("deleteSupplier(...)");
    }
}
