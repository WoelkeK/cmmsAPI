package pl.medos.cmmsApi.service.impl;

import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.exception.InvoiceNotFoundException;
import pl.medos.cmmsApi.model.Invoice;
import pl.medos.cmmsApi.repository.InvoiceRepository;
import pl.medos.cmmsApi.repository.entity.InvoiceEntity;
import pl.medos.cmmsApi.service.InvoiceService;
import pl.medos.cmmsApi.service.mapper.InvoiceMapper;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class InvoiceServiceImpl implements InvoiceService{

    private static final Logger LOGGER = Logger.getLogger(InvoiceServiceImpl.class.getName());

    private InvoiceRepository invoiceRepository;
    private InvoiceMapper invoiceMapper;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
    }

    public List<Invoice> findAllInvoices() {
        LOGGER.info("findAllInvoices()");
        List<InvoiceEntity> invoiceEntities = invoiceRepository.findAll();
        List<Invoice> invoiceModels = invoiceMapper.list(invoiceEntities);
        LOGGER.info("findAllInvoices(...)");
        return invoiceModels;
    }

    public Invoice createInvoice(Invoice invoice) {
        LOGGER.info("createInvoice(" + invoice + ")");
        InvoiceEntity invoiceEntity = invoiceMapper.modelToEntity(invoice);
        InvoiceEntity savedInvoiceEntity = invoiceRepository.save(invoiceEntity);
        Invoice savedDepartmentModel = invoiceMapper.entityToModel(savedInvoiceEntity);
        LOGGER.info("createInvoice(...)" + savedDepartmentModel);
        return savedDepartmentModel;
    }

    public Invoice findInvoiceById(Long id) throws InvoiceNotFoundException {
        LOGGER.info("findInvoiceById(" + id + ")");
        Optional<InvoiceEntity> optionalInvoiceEntity = invoiceRepository.findById(id);
        InvoiceEntity invoiceEntity = optionalInvoiceEntity.orElseThrow(
                () -> new InvoiceNotFoundException("Brak faktury o podanym id " + id));
        Invoice invoiceModel = invoiceMapper.entityToModel(invoiceEntity);
        LOGGER.info("findInvoiceById(...)" + invoiceModel);
        return invoiceModel;
    }

    public Invoice updateInvoice(Invoice invoice) {
        LOGGER.info("updateInvoice()" + invoice);
        InvoiceEntity invoiceEntity = invoiceMapper.modelToEntity(invoice);
        InvoiceEntity updatedInvoiceEntity = invoiceRepository.save(invoiceEntity);
        Invoice updatedInvoiceModel = invoiceMapper.entityToModel(updatedInvoiceEntity);
        LOGGER.info("updateInvoice(...) " + updatedInvoiceModel);
        return updatedInvoiceModel;
    }

    public void deleteInvoice(Long id) {
        LOGGER.info("deleteInvoice()");
        invoiceRepository.deleteById(id);
        LOGGER.info("deleteInvoice(...)");
    }
}
