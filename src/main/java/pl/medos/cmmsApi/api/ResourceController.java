package pl.medos.cmmsApi.api;

import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.ResourceNotFoundException;
import pl.medos.cmmsApi.model.Resource;
import pl.medos.cmmsApi.service.impl.ResourceServiceImpl;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class ResourceController {

    private static final Logger LOGGER = Logger.getLogger(ResourceController.class.getName());

    private ResourceServiceImpl resourceServiceImpl;

    public ResourceController(ResourceServiceImpl resourceServiceImpl) {
        this.resourceServiceImpl = resourceServiceImpl;
    }

    @GetMapping("/resources")
    public List<Resource> list() {
        LOGGER.info("lista()");
        List<Resource> resources = resourceServiceImpl.findAllResources();
        LOGGER.info("list(...)");
        return resources;
    }

    @PostMapping("/resources")
    public Resource create(@RequestBody Resource resource) {
        LOGGER.info("create()" + resource);
        Resource savedResource = resourceServiceImpl.createResource(resource);
        LOGGER.info("create(...)" + savedResource);
        return savedResource;
    }

    @GetMapping("/resources/{id}")
    public Resource read(@PathVariable(name = "id") Long id) throws ResourceNotFoundException {
        LOGGER.info("read()");
        Resource resourceModel = resourceServiceImpl.findResourceById(id);
        LOGGER.info("read(...)" + resourceModel);
        return resourceModel;
    }

    @PutMapping("/resources")
    public Resource update(@RequestBody Resource resource) {
        LOGGER.info("update()" + resource);
        Resource updatedResource = resourceServiceImpl.updateResource(resource);
        LOGGER.info("update(...)" + updatedResource);
        return updatedResource;
    }

    @DeleteMapping("/resources/{id}")
    public String delete(Long id) {
        LOGGER.info("delete(" + id + ")");
        resourceServiceImpl.deleteResource(id);
        LOGGER.info("delete(...)");
        return "Response 200: Record: " + id + " deleted";
    }
}
