package pl.medos.cmmsApi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.MaterialNotFoundException;
import pl.medos.cmmsApi.model.Material;
import pl.medos.cmmsApi.service.MaterialService;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/materials")
public class WebMaterialController {

    private static final Logger LOGGER = Logger.getLogger(WebMaterialController.class.getName());

    private MaterialService materialService;

    public WebMaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping
    public String listView(ModelMap modelMap) {
        LOGGER.info("listView()");
        List<Material> materials = materialService.list();
        modelMap.addAttribute("materials", materials);
        LOGGER.info("listView(...)");
        return "templates/list-materials.html";
    }

    @GetMapping(value = "/create")
    public String createView(ModelMap modelMap) {
        LOGGER.info("createView()");
        modelMap.addAttribute("material", new Material());
        LOGGER.info("createView(...)");
        return "create-material.html";
    }

    @PostMapping(value = "/create")
    public String create(@ModelAttribute(name = "Material") Material material) {
        LOGGER.info("create()");
        Material savedMaterial = materialService.create(material);
        LOGGER.info("create(...)" + savedMaterial);
        return "redirect:/materials";
    }

    @GetMapping(value = "/read/{id}")
    public String read(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws MaterialNotFoundException {
        LOGGER.info("read(" + id + ")");
        Material materialModel = materialService.read(id);
        modelMap.addAttribute("material", materialModel);
        LOGGER.info("read(...)" + materialModel);
        return "read-material.html";
    }

    @GetMapping(value = "/update/{id}")
    public String updateView(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws MaterialNotFoundException {
        LOGGER.info("update()" + id);
        Material material = materialService.read(id);
        modelMap.addAttribute("material", material);
        LOGGER.info("update(...)");
        return "update-material.html";
    }

    @PutMapping(value = "/update")
    public String update(
            @ModelAttribute(name = "material") Material material) throws MaterialNotFoundException {
        LOGGER.info("update()" + material);
        Material updatedMaterial = materialService.update(material);
        LOGGER.info("update(...)" + updatedMaterial);
        return "redirect:/materials";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(Long id) {
        LOGGER.info("delete(" + id + ")");
        materialService.delete(id);
        LOGGER.info("delete(...)");
        return "Response 200: Record: " + id + " deleted";
    }
}
