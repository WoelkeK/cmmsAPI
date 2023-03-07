package pl.medos.cmmsApi.service;

import pl.medos.cmmsApi.exception.InvoiceNotFoundException;
import pl.medos.cmmsApi.model.Invoice;
import java.util.List;

public interface InvoiceService {

    List<Invoice> findAllInvoices();

    Invoice createInvoice(Invoice invoice);

    Invoice findInvoiceById(Long id) throws InvoiceNotFoundException;

    Invoice updateInvoice(Invoice invoice);

    void deleteInvoice(Long id);
}
