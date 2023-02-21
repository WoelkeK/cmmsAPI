package pl.medos.cmmsApi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.ResourceNotFoundException;
import pl.medos.cmmsApi.exception.SupplierNotFoundException;
import pl.medos.cmmsApi.model.Resource;
import pl.medos.cmmsApi.model.Supplier;
import pl.medos.cmmsApi.service.InvoiceService;
import pl.medos.cmmsApi.service.ResourceService;
import pl.medos.cmmsApi.service.SupplierService;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/suppliers")
public class WebSupplierController {

    private static final Logger LOGGER = Logger.getLogger(WebSupplierController.class.getName());

    private SupplierService supplierService;

    public WebSupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public String listView(ModelMap modelMap) {
        LOGGER.info("listView()");
        List<Supplier> suppliers = supplierService.list();
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
        Supplier savedSupplier = supplierService.create(supplier);
        LOGGER.info("create(...)" + savedSupplier);
        return "redirect:/suppliers";
    }

    @GetMapping(value = "/read/{id}")
    public String read(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws SupplierNotFoundException {
        LOGGER.info("read(" + id + ")");
        Supplier supplierModel = supplierService.read(id);
        modelMap.addAttribute("supplier", supplierModel);
        LOGGER.info("read(...)" + supplierModel);
        return "read-supplier.html";
    }

    @GetMapping(value = "/update/{id}")
    public String updateView(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws SupplierNotFoundException {
        LOGGER.info("update()" + id);
       Supplier supplier = supplierService.read(id);
        modelMap.addAttribute("supplier", supplier);
        LOGGER.info("update(...)");
        return "create-supplier.html";
    }

    @PutMapping(value = "/update")
    public String update(
            @ModelAttribute(name = "supplier") Supplier supplier) throws SupplierNotFoundException {
        LOGGER.info("update()" + supplier);
        Supplier updatedsupplier = supplierService.update(supplier);
        LOGGER.info("update(...)" + updatedsupplier);
        return "redirect:/suppliers";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable(name = "id") Long id) {
        LOGGER.info("delete(" + id + ")");
        supplierService.delete(id);
        LOGGER.info("delete(...)");
        return "redirect:/suppliers";
    }
}
