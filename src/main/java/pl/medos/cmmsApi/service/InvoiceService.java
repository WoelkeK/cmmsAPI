package pl.medos.cmmsApi.service;

import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.exception.DepartmentNotFoundException;
import pl.medos.cmmsApi.exception.InvoiceNotFoundException;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Invoice;
import pl.medos.cmmsApi.repository.DepartmentRepository;
import pl.medos.cmmsApi.repository.InvoiceRepository;
import pl.medos.cmmsApi.repository.entity.DepartmentEntity;
import pl.medos.cmmsApi.repository.entity.InvoiceEntity;
import pl.medos.cmmsApi.service.mapper.DepartmentMapper;
import pl.medos.cmmsApi.service.mapper.InvoiceMapper;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class InvoiceService {

    private static final Logger LOGGER = Logger.getLogger(InvoiceService.class.getName());

    private InvoiceRepository invoiceRepository;
    private InvoiceMapper invoiceMapper;

    public InvoiceService(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
    }

    public List<Invoice> list() {
        LOGGER.info("list()");
        List<InvoiceEntity> invoiceEntities = invoiceRepository.findAll();
        List<Invoice> invoiceModels = invoiceMapper.list(invoiceEntities);
        LOGGER.info("list(...)");
        return invoiceModels;
    }

    public Invoice create(Invoice invoice) {
        LOGGER.info("create(" + invoice + ")");
        InvoiceEntity invoiceEntity = invoiceMapper.modelToEntity(invoice);
        InvoiceEntity savedInvoiceEntity = invoiceRepository.save(invoiceEntity);
        Invoice savedDepartmentModel = invoiceMapper.entityToModel(savedInvoiceEntity);
        LOGGER.info("create(...)" + savedDepartmentModel);
        return savedDepartmentModel;
    }

    public Invoice read(Long id) throws InvoiceNotFoundException {
        LOGGER.info("read(" + id + ")");
        Optional<InvoiceEntity> optionalInvoiceEntity = invoiceRepository.findById(id);
        InvoiceEntity invoiceEntity = optionalInvoiceEntity.orElseThrow(
                () -> new InvoiceNotFoundException("Brak faktury o podanym id " + id));
        Invoice invoiceModel = invoiceMapper.entityToModel(invoiceEntity);
        LOGGER.info("read(...)" + invoiceModel);
        return invoiceModel;
    }

    public Invoice update(Invoice invoice) {
        LOGGER.info("update()" + invoice);
        InvoiceEntity invoiceEntity = invoiceMapper.modelToEntity(invoice);
        InvoiceEntity updatedInvoiceEntity = invoiceRepository.save(invoiceEntity);
        Invoice updatedInvoiceModel = invoiceMapper.entityToModel(updatedInvoiceEntity);
        LOGGER.info("update(...) " + updatedInvoiceModel);
        return updatedInvoiceModel;
    }

    public String delete(Long id) {
        LOGGER.info("delete()");
        invoiceRepository.deleteById(id);
        LOGGER.info("delete(...)");
        return "Response: 200 Record " + id + " deleted!";
    }
}
