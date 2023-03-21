package pl.medos.cmmsApi.service;

import pl.medos.cmmsApi.exception.SupplierNotFoundException;
import pl.medos.cmmsApi.model.Supplier;

import java.util.List;

public interface SupplierService {
    List<Supplier> findAllSuppliers();

    Supplier createSupplier(Supplier supplier);

    Supplier findSupplierById(Long id) throws SupplierNotFoundException;

    Supplier updateSupplier(Supplier supplier, Long id);

    void deleteSupplier(Long id);
}
