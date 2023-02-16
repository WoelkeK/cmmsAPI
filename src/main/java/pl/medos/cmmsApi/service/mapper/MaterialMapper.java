package pl.medos.cmmsApi.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.medos.cmmsApi.model.Material;
import pl.medos.cmmsApi.repository.MaterialRepository;
import pl.medos.cmmsApi.repository.entity.MachineEntity;
import pl.medos.cmmsApi.repository.entity.MaterialEntity;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class MaterialMapper {

    private static final Logger LOGGER = Logger.getLogger(MaterialMapper.class.getName());

    private MaterialEntity materialEntity;

    public List<Material> list(List<MaterialEntity> materialEntities) {

        LOGGER.info("list()" + materialEntities);
        List<Material> materialModels =materialEntities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());

        LOGGER.info("list(...)");
        return materialModels;
    }

    public Material entityToModel(MaterialEntity materialEntity) {
        LOGGER.info("entityToModel()" + materialEntity);
        ModelMapper modelMapper = new ModelMapper();
        Material materialModel = modelMapper.map(materialEntity, Material.class);

        LOGGER.info("entityToModel(...)" + materialModel);
        return materialModel;

    }

    public MaterialEntity modelToEntity(Material material) {
        LOGGER.info("modelToEntity()" + material);
        ModelMapper modelMapper = new ModelMapper();
        MaterialEntity materialEntity = modelMapper.map(material, MaterialEntity.class);
        LOGGER.info("modelToEntity(...)" + materialEntity);
        return materialEntity;
    }
}
