package pl.medos.cmmsApi.controllers;

import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.MaterialNotFoundException;
import pl.medos.cmmsApi.model.Material;
import pl.medos.cmmsApi.service.MaterialService;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class MaterialController {

    private static final Logger LOGGER = Logger.getLogger(MaterialController.class.getName());

    private MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping("/material")
    public List<Material> list() {
        LOGGER.info("lista()");
        List<Material> materials = materialService.list();
        LOGGER.info("list(...)");
        return materials;
    }

    @PostMapping("/material")
    public Material create(@RequestBody Material material) {
        LOGGER.info("create()" + material);
        Material savedMaterial = materialService.create(material);
        LOGGER.info("create(...)" + savedMaterial);
        return savedMaterial;
    }

    @GetMapping("/material/{id}")
    public Material read(@PathVariable(name = "id") Long id) throws MaterialNotFoundException {
        LOGGER.info("read()");
        Material materialModel = materialService.read(id);
        LOGGER.info("read(...)" + materialModel);
        return materialModel;
    }

    @PutMapping("/material")
    public Material update(@RequestBody Material material) {
        LOGGER.info("update()" + material);
        Material updatedMaterial = materialService.update(material);
        LOGGER.info("update(...)" + updatedMaterial);
        return updatedMaterial;
    }

    @DeleteMapping("/material/{id}")
    public String delete(Long id) {
        LOGGER.info("delete(" + id + ")");
        materialService.delete(id);
        LOGGER.info("delete(...)");
        return "Response 200: Record: " + id + " deleted";
    }
}
