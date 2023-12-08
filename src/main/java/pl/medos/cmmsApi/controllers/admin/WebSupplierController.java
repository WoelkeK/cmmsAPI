package pl.medos.cmmsApi.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.SupplierNotFoundException;
import pl.medos.cmmsApi.model.Supplier;
import pl.medos.cmmsApi.service.impl.SupplierServiceImpl;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/suppliers")
public class WebSupplierController {

    private static final Logger LOGGER = Logger.getLogger(WebSupplierController.class.getName());

    private SupplierServiceImpl supplierServiceImpl;

    public WebSupplierController(SupplierServiceImpl supplierServiceImpl) {
        this.supplierServiceImpl = supplierServiceImpl;
    }

    @GetMapping
    public String listView(ModelMap modelMap) {
        LOGGER.info("listView()");
        List<Supplier> suppliers = supplierServiceImpl.findAllSuppliers();
        modelMap.addAttribute("suppliers", suppliers);
        LOGGER.info("listView(...)");
        return "list-supplier.html";
    }

    @GetMapping(value = "/create")
    public String createView(ModelMap modelMap) {
        LOGGER.info("createView()");
        modelMap.addAttribute("supplier", new Supplier());
        LOGGER.info("createView(...)");
        return "create-supplier.html";
    }

    @PostMapping(value = "/create")
    public String create(@ModelAttribute(name = "supplier") Supplier supplier) {
        LOGGER.info("create()");
        Supplier savedSupplier = supplierServiceImpl.createSupplier(supplier);
        LOGGER.info("create(...)" + savedSupplier);
        return "redirect:/suppliers";
    }

    @GetMapping(value = "/read/{id}")
    public String read(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws SupplierNotFoundException {
        LOGGER.info("read(" + id + ")");
        Supplier supplierModel = supplierServiceImpl.findSupplierById(id);
        modelMap.addAttribute("supplier", supplierModel);
        LOGGER.info("read(...)" + supplierModel);
        return "read-supplier.html";
    }

    @GetMapping(value = "/update/{id}")
    public String updateView(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws SupplierNotFoundException {
        LOGGER.info("update()" + id);
        Supplier supplier = supplierServiceImpl.findSupplierById(id);
        modelMap.addAttribute("supplier", supplier);
        LOGGER.info("update(...)");
        return "create-supplier.html";
    }

    @PutMapping(value = "/update/{id}")
    public String update(
            @PathVariable(name = "id") Long id,
            @ModelAttribute(name = "supplier") Supplier supplier) throws SupplierNotFoundException {
        LOGGER.info("update()" + supplier);
        Supplier updatedsupplier = supplierServiceImpl.updateSupplier(supplier, id);
        LOGGER.info("update(...)" + updatedsupplier);
        return "redirect:/suppliers";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable(name = "id") Long id) {
        LOGGER.info("delete(" + id + ")");
        supplierServiceImpl.deleteSupplier(id);
        LOGGER.info("delete(...)");
        return "redirect:/suppliers";
    }
}
