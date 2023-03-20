package pl.medos.cmmsApi.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.SupplierNotFoundException;
import pl.medos.cmmsApi.model.Supplier;
import pl.medos.cmmsApi.service.SupplierService;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class SupplierController {

    private static final Logger LOGGER = Logger.getLogger(SupplierController.class.getName());

    private final SupplierService supplierService;

    @GetMapping("/suppliers")
    public void getAllSuppliers() {
        LOGGER.info("getAllSuppliers()");
        List<Supplier> allSuppliers = supplierService.list();
        LOGGER.info("getAllSuppliers(...)");

    }

    @PostMapping("/supplier")
    public void createSupplier() {
        LOGGER.info("createSupplier)");
        Supplier createdSupplier = supplierService.create();
        LOGGER.info("createSupplier(...)" + createdSupplier);

    }

    @GetMapping("/supplier/{id}")
    public void findSupplierById(@PathVariable(name = "id") Long id) throws SupplierNotFoundException {
        LOGGER.info("findSupplierById()");
        Supplier supplierById = supplierService.read(id);
        LOGGER.info("findSupplierById(...)");
    }

    @PutMapping("/supplier/{id}")
    public void updateSupplier(@ModelAttribute Supplier supplier, @PathVariable(name = "id") Long id) {
        LOGGER.info("updatesupplier()" + id);
        Supplier updatedsupplier = supplierService.update(supplier, id);
        LOGGER.info("updatesupplier(...)");

    }

    @DeleteMapping("/supplier/{id}")
    public void deleteSupplier(@PathVariable(name = "id") Long id) {
        LOGGER.info("deleteSupplier()");
        supplierService.delete(id);
        LOGGER.info("deleteSupplier(...)");

    }
}
