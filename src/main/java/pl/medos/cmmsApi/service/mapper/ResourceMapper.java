package pl.medos.cmmsApi.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.medos.cmmsApi.model.Resource;
import pl.medos.cmmsApi.repository.entity.ResourceEntity;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class ResourceMapper {

    private static final Logger LOGGER = Logger.getLogger(ResourceMapper.class.getName());

    private ResourceEntity resourceEntity;

    public List<Resource> list(List<ResourceEntity> resourceEntities) {

        LOGGER.info("list()" + resourceEntities);
        List<Resource> resourceModels =resourceEntities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());

        LOGGER.info("list(...)");
        return resourceModels;
    }

    public Resource entityToModel(ResourceEntity resourceEntity) {
        LOGGER.info("entityToModel()" + resourceEntity);
        ModelMapper modelMapper = new ModelMapper();
        Resource resourcelModel = modelMapper.map(resourceEntity, Resource.class);

        LOGGER.info("entityToModel(...)" + resourcelModel);
        return resourcelModel;

    }

    public ResourceEntity modelToEntity(Resource resource) {
        LOGGER.info("modelToEntity()" + resource);
        ModelMapper modelMapper = new ModelMapper();
        ResourceEntity resourceEntity = modelMapper.map(resource, ResourceEntity.class);
        LOGGER.info("modelToEntity(...)" + resourceEntity);
        return resourceEntity;
    }
}
