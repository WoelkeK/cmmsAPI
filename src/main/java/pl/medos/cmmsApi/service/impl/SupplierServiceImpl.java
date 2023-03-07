package pl.medos.cmmsApi.service.impl;

import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.exception.SupplierNotFoundException;
import pl.medos.cmmsApi.model.Supplier;
import pl.medos.cmmsApi.repository.SupplierRepository;
import pl.medos.cmmsApi.repository.entity.SupplierEntity;
import pl.medos.cmmsApi.service.mapper.SupplierMapper;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class SupplierServiceImpl {

    private static final Logger LOGGER = Logger.getLogger(SupplierServiceImpl.class.getName());

    private SupplierRepository supplierRepository;
    private SupplierMapper supplierMapper;

    public SupplierServiceImpl(SupplierRepository supplierRepository, SupplierMapper supplierMapper) {
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
    }

    public List<Supplier> list() {
        LOGGER.info("list()");
        List<SupplierEntity> supplierEntities = supplierRepository.findAll();
        List<Supplier> supplierModels = supplierMapper.list(supplierEntities);
        LOGGER.info("list(...)");
        return supplierModels;
    }

    public Supplier create(Supplier supplier) {
        LOGGER.info("create(" + supplier + ")");
       SupplierEntity supplierEntity = supplierMapper.modelToEntity(supplier);
        SupplierEntity savedSupplierEntity= supplierRepository.save(supplierEntity);
        Supplier savedSupplierModel= supplierMapper.entityToModel(savedSupplierEntity);
        LOGGER.info("create(...)" + savedSupplierModel);
        return savedSupplierModel;
    }

    public Supplier read(Long id) throws SupplierNotFoundException{
        LOGGER.info("read(" + id + ")");
        Optional<SupplierEntity> optionalSupplierEntity = supplierRepository.findById(id);
        SupplierEntity supplierEntity= optionalSupplierEntity.orElseThrow(
                () -> new SupplierNotFoundException("Brak dostawcy o podanym id " + id));
        Supplier supplierModel= supplierMapper.entityToModel(supplierEntity);
        LOGGER.info("read(...)" + supplierModel);
        return supplierModel;
    }

    public Supplier update(Supplier supplier) {
        LOGGER.info("update()" + supplier);
        SupplierEntity supplierEntity= supplierMapper.modelToEntity(supplier);
        SupplierEntity updatedSupplierEntity =supplierRepository.save(supplierEntity);
        Supplier updatedSupplierModel = supplierMapper.entityToModel(updatedSupplierEntity);
        LOGGER.info("update(...) " + updatedSupplierModel);
        return updatedSupplierModel;
    }

    public String delete(Long id) {
        LOGGER.info("delete()");
        supplierRepository.deleteById(id);
        LOGGER.info("delete(...)");
        return "Response: 200 Record " + id + " deleted!";
    }
}
