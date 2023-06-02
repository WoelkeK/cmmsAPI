package pl.medos.cmmsApi.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import pl.medos.cmmsApi.model.Software;
import pl.medos.cmmsApi.repository.entity.SoftwareEntity;
import pl.medos.cmmsApi.service.SoftwareService;

import java.util.List;
import java.util.logging.Logger;

@Component
public class SoftwareMapper {

    private static final Logger LOGGER = Logger.getLogger(SoftwareMapper.class.getName());

    public Software mapEntityToModel(SoftwareEntity softwareEntity) {

        LOGGER.info("mapEntityToModel()");
        ModelMapper modelMapper = new ModelMapper();
        Software software = modelMapper.map(softwareEntity, Software.class);
        LOGGER.info("mapEntityToModel(...)");
        return software;
    }

    public SoftwareEntity mapModelToEntity(Software software) {
        LOGGER.info("mapModelToEntity()");
        ModelMapper modelMapper = new ModelMapper();
        SoftwareEntity softwareEntity = modelMapper.map(software, SoftwareEntity.class);
        LOGGER.info("mapModelToEntity(...)");
        return softwareEntity;
    }

    public List<Software> listEntitiesToModels(List<SoftwareEntity> softwareEntities) {
        LOGGER.info("listEntitiesToModels()");
        List<Software> softwares = softwareEntities.stream()
                .map(this::mapEntityToModel)
                .toList();
        LOGGER.info("listEntitiesToModels(...)");
        return softwares;
    }

    public List<SoftwareEntity> listModelsToEntity(List<Software> softwares) {
        LOGGER.info("listModelsToEntities()");
        List<SoftwareEntity> softwareEntities = softwares.stream()
                .map(this::mapModelToEntity)
                .toList();
        LOGGER.info("listModelsToEntities(...)");
        return softwareEntities;
    }

    public Page<Software> entitiesToModelsPage(Page<SoftwareEntity> softwareEntities) {
        LOGGER.info("pageEntitiesToModels()");
        ModelMapper modelMapper = new ModelMapper();
        Page<Software> softwares = softwareEntities.map(SoftwareEntity -> modelMapper.map(SoftwareEntity, Software.class));
        LOGGER.info("pageEntitiesToModels(...)");
        return softwares;
    }
}
