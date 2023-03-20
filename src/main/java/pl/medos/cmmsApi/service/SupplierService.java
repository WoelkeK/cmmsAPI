package pl.medos.cmmsApi.service;

import pl.medos.cmmsApi.exception.SupplierNotFoundException;
import pl.medos.cmmsApi.model.Supplier;

import java.util.List;

public interface SupplierService {
    List<Supplier> list();

    Supplier create();

    Supplier read(Long id) throws SupplierNotFoundException;

    Supplier update(Supplier supplier, Long id);

    void delete(Long id);
}
