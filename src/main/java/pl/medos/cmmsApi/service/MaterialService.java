package pl.medos.cmmsApi.service;

import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.exception.MaterialNotFoundException;
import pl.medos.cmmsApi.model.Material;
import pl.medos.cmmsApi.repository.MaterialRepository;
import pl.medos.cmmsApi.repository.entity.MaterialEntity;
import pl.medos.cmmsApi.service.mapper.MachineMapper;
import pl.medos.cmmsApi.service.mapper.MaterialMapper;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class MaterialService {
    private static final Logger LOGGER = Logger.getLogger(MaterialService.class.getName());
    private MaterialRepository materialRepository;
    private MaterialMapper materialMapper;

    public MaterialService(MaterialRepository materialRepository, MaterialMapper materialMapper) {
        this.materialRepository = materialRepository;
        this.materialMapper = materialMapper;
    }

    public List<Material> list() {
        LOGGER.info("list()");
        List<MaterialEntity> materialEntities = materialRepository.findAll();
        List<Material> materials = materialMapper.list(materialEntities);
        LOGGER.info("list(...)");
        return materials;
    }

    public Material create(Material material) {
        LOGGER.info("create()" + material);
        MaterialEntity materialEntity = materialMapper.modelToEntity(material);
        MaterialEntity savedMaterialEntity = materialRepository.save(materialEntity);
        Material savedMaterialModel = materialMapper.entityToModel(savedMaterialEntity);
        LOGGER.info("create(...)" + savedMaterialModel);
        return savedMaterialModel;
    }

    public Material read(Long id) throws MaterialNotFoundException {
        LOGGER.info("read(" + id + ")");
        Optional<MaterialEntity> optionalMaterialEntity = materialRepository.findById(id);
        MaterialEntity materialEntity = optionalMaterialEntity.orElseThrow(
                () -> new MaterialNotFoundException("Brak materiału o podanym id " + id));
        Material materialModel = materialMapper.entityToModel(materialEntity);
        LOGGER.info("read(...)" + materialModel);
        return materialModel;
    }

    public Material update(Material material) {
        LOGGER.info("update()" + material);
        MaterialEntity materialEntity = materialMapper.modelToEntity(material);
        MaterialEntity updatedMaterialEntity = materialRepository.save(materialEntity);
        Material updatetMaterialModel = materialMapper.entityToModel(updatedMaterialEntity);
        LOGGER.info("update(...)" + updatetMaterialModel);
        return updatetMaterialModel;
    }

    public String delete(Long id) {
        LOGGER.info("delete()");
        materialRepository.deleteById(id);
        LOGGER.info("delete(..)");
        return "Response 200: Record " + id + " deleted";
    }


}
