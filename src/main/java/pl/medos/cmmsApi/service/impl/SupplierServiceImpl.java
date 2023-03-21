package pl.medos.cmmsApi.service.impl;

import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.exception.SupplierNotFoundException;
import pl.medos.cmmsApi.model.Supplier;
import pl.medos.cmmsApi.repository.SupplierRepository;
import pl.medos.cmmsApi.repository.entity.SupplierEntity;
import pl.medos.cmmsApi.service.SupplierService;
import pl.medos.cmmsApi.service.mapper.SupplierMapper;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class SupplierServiceImpl implements SupplierService {

    private static final Logger LOGGER = Logger.getLogger(SupplierServiceImpl.class.getName());

    private SupplierRepository supplierRepository;
    private SupplierMapper supplierMapper;

    public SupplierServiceImpl(SupplierRepository supplierRepository, SupplierMapper supplierMapper) {
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
    }

    public List<Supplier> findAllSuppliers() {
        LOGGER.info("list()");
        List<SupplierEntity> supplierEntities = supplierRepository.findAll();
        List<Supplier> supplierModels = supplierMapper.list(supplierEntities);
        LOGGER.info("list(...)");
        return supplierModels;
    }

    public Supplier createSupplier() {
        LOGGER.info("create()");
        SupplierEntity supplierEntity = supplierMapper.modelToEntity(new Supplier());
        SupplierEntity savedSupplierEntity = supplierRepository.save(supplierEntity);
        Supplier savedSupplierModel = supplierMapper.entityToModel(savedSupplierEntity);
        LOGGER.info("create(...)" + savedSupplierModel);
        return savedSupplierModel;
    }

    public Supplier findSupplierById(Long id) throws SupplierNotFoundException {
        LOGGER.info("read(" + id + ")");
        Optional<SupplierEntity> optionalSupplierEntity = supplierRepository.findById(id);
        SupplierEntity supplierEntity = optionalSupplierEntity.orElseThrow(
                () -> new SupplierNotFoundException("Brak dostawcy o podanym id " + id));
        Supplier supplierModel = supplierMapper.entityToModel(supplierEntity);
        LOGGER.info("read(...)" + supplierModel);
        return supplierModel;
    }

    public Supplier updateSupplier(Supplier supplier, Long id) {
        LOGGER.info("update()" + supplier);
        SupplierEntity supplierEntity = supplierMapper.modelToEntity(supplier);
        SupplierEntity updatedSupplierEntity = supplierRepository.save(supplierEntity);
        Supplier updatedSupplierModel = supplierMapper.entityToModel(updatedSupplierEntity);
        LOGGER.info("update(...) " + updatedSupplierModel);
        return updatedSupplierModel;
    }

    public void deleteSupplier(Long id) {
        LOGGER.info("delete()");
        supplierRepository.deleteById(id);
        LOGGER.info("delete(...)");
    }
}
