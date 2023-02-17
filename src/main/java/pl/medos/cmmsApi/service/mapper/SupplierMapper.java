package pl.medos.cmmsApi.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.medos.cmmsApi.model.Invoice;
import pl.medos.cmmsApi.model.Supplier;
import pl.medos.cmmsApi.repository.entity.InvoiceEntity;
import pl.medos.cmmsApi.repository.entity.SupplierEntity;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class SupplierMapper {

    private static final Logger LOGGER = Logger.getLogger(SupplierMapper.class.getName());

    private SupplierEntity supplierEntity;

    public List<Supplier> list(List<SupplierEntity> supplierEntities) {

        LOGGER.info("list()" + supplierEntities);
        List<Supplier> supplierModels =supplierEntities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());

        LOGGER.info("list(...)");
        return supplierModels;
    }

    public Supplier entityToModel(SupplierEntity supplierEntity) {
        LOGGER.info("entityToModel()" + supplierEntity);
        ModelMapper modelMapper = new ModelMapper();
        Supplier supplierModel = modelMapper.map(supplierEntity, Supplier.class);
        LOGGER.info("entityToModel(...)" + supplierModel);
        return supplierModel;
    }

    public SupplierEntity modelToEntity(Supplier supplier) {
        LOGGER.info("modelToEntity()" + supplier);
        ModelMapper modelMapper = new ModelMapper();
        SupplierEntity supplierEntity= modelMapper.map(supplier, SupplierEntity.class);
        LOGGER.info("modelToEntity(...)" + supplierEntity);
        return supplierEntity;
    }
}
