package pl.medos.cmmsApi.controllers;

import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.ResourceNotFoundException;
import pl.medos.cmmsApi.model.Resource;
import pl.medos.cmmsApi.service.ResourceService;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class ResourceController {

    private static final Logger LOGGER = Logger.getLogger(ResourceController.class.getName());

    private ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping("/resources")
    public List<Resource> list() {
        LOGGER.info("lista()");
        List<Resource> resources = resourceService.list();
        LOGGER.info("list(...)");
        return resources;
    }

    @PostMapping("/resources")
    public Resource create(@RequestBody Resource resource) {
        LOGGER.info("create()" + resource);
        Resource savedResource = resourceService.create(resource);
        LOGGER.info("create(...)" + savedResource);
        return savedResource;
    }

    @GetMapping("/resources/{id}")
    public Resource read(@PathVariable(name = "id") Long id) throws ResourceNotFoundException {
        LOGGER.info("read()");
        Resource resourceModel = resourceService.read(id);
        LOGGER.info("read(...)" + resourceModel);
        return resourceModel;
    }

    @PutMapping("/resources")
    public Resource update(@RequestBody Resource resource) {
        LOGGER.info("update()" + resource);
        Resource updatedResource = resourceService.update(resource);
        LOGGER.info("update(...)" + updatedResource);
        return updatedResource;
    }

    @DeleteMapping("/resources/{id}")
    public String delete(Long id) {
        LOGGER.info("delete(" + id + ")");
        resourceService.delete(id);
        LOGGER.info("delete(...)");
        return "Response 200: Record: " + id + " deleted";
    }
}
