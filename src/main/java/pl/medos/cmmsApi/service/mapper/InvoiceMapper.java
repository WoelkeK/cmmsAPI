package pl.medos.cmmsApi.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.medos.cmmsApi.model.Invoice;
import pl.medos.cmmsApi.model.Resource;
import pl.medos.cmmsApi.repository.entity.InvoiceEntity;
import pl.medos.cmmsApi.repository.entity.ResourceEntity;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class InvoiceMapper {

    private static final Logger LOGGER = Logger.getLogger(InvoiceMapper.class.getName());

    private InvoiceEntity invoiceEntity;

    public List<Invoice> list(List<InvoiceEntity> invoiceEntities) {

        LOGGER.info("list()" + invoiceEntities);
        List<Invoice> invoiceModels =invoiceEntities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());

        LOGGER.info("list(...)");
        return invoiceModels;
    }

    public Invoice entityToModel(InvoiceEntity invoiceEntity) {
        LOGGER.info("entityToModel()" + invoiceEntity);
        ModelMapper modelMapper = new ModelMapper();
        Invoice invoiceModel = modelMapper.map(invoiceEntity, Invoice.class);
        LOGGER.info("entityToModel(...)" + invoiceModel);
        return invoiceModel;

    }

    public InvoiceEntity modelToEntity(Invoice invoice) {
        LOGGER.info("modelToEntity()" + invoice);
        ModelMapper modelMapper = new ModelMapper();
        InvoiceEntity invoiceEntity= modelMapper.map(invoice, InvoiceEntity.class);
        LOGGER.info("modelToEntity(...)" + invoiceEntity);
        return invoiceEntity;
    }
}
