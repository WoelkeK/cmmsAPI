package pl.medos.cmmsApi.service.impl;

import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.exception.ResourceNotFoundException;
import pl.medos.cmmsApi.model.Resource;
import pl.medos.cmmsApi.repository.ResourceRepository;
import pl.medos.cmmsApi.repository.entity.ResourceEntity;
import pl.medos.cmmsApi.service.ResourceService;
import pl.medos.cmmsApi.service.mapper.ResourceMapper;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ResourceServiceImpl implements ResourceService {
    private static final Logger LOGGER = Logger.getLogger(ResourceServiceImpl.class.getName());

    private ResourceMapper resourceMapper;
    private ResourceRepository resourceRepository;

    public ResourceServiceImpl(ResourceMapper resourceMapper, ResourceRepository resourceRepository) {
        this.resourceMapper = resourceMapper;
        this.resourceRepository = resourceRepository;
    }

    public List<Resource> findAllResources() {
        LOGGER.info("list()");
        List<ResourceEntity> resourceEntities = resourceRepository.findAll();
        List<Resource> resources = resourceMapper.list(resourceEntities);
        LOGGER.info("list(...)");
        return resources;
    }

    public Resource createResource(Resource resource) {
        LOGGER.info("create()" + resource);
        ResourceEntity resourceEntity = resourceMapper.modelToEntity(resource);
        ResourceEntity savedResourceEntity = resourceRepository.save(resourceEntity);
        Resource savedResourceModel = resourceMapper.entityToModel(savedResourceEntity);
        LOGGER.info("create(...)" + savedResourceModel);
        return savedResourceModel;
    }

    public Resource findResourceById(Long id) throws ResourceNotFoundException {
        LOGGER.info("read(" + id + ")");
        Optional<ResourceEntity> optionalResourceEntity = resourceRepository.findById(id);
        ResourceEntity resourceEntity = optionalResourceEntity.orElseThrow(
                () -> new ResourceNotFoundException("Brak materiału o podanym id " + id));
        Resource resourceModel = resourceMapper.entityToModel(resourceEntity);
        LOGGER.info("read(...)" + resourceModel);
        return resourceModel;
    }

    public Resource updateResource(Resource resource) {
        LOGGER.info("update()" + resource);
        ResourceEntity resourceEntity = resourceMapper.modelToEntity(resource);
        ResourceEntity updatedResourceEntity = resourceRepository.save(resourceEntity);
        Resource updatedResourceModel = resourceMapper.entityToModel(updatedResourceEntity);
        LOGGER.info("update(...)" + updatedResourceModel);
        return updatedResourceModel;
    }

    public void deleteResource(Long id) {
        LOGGER.info("delete()");
        resourceRepository.deleteById(id);
        LOGGER.info("delete(..)");
    }
}
