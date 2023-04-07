package pl.medos.cmmsApi.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.model.Engineer;
import pl.medos.cmmsApi.repository.entity.EmployeeEntity;
import pl.medos.cmmsApi.repository.entity.EngineerEntity;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class EngineerMapper {

    private static final Logger LOGGER = Logger.getLogger(EngineerMapper.class.getName());

    private EngineerEntity engineerEntity;

    public List<Engineer> listModels(List<EngineerEntity> engineerEntities ){
        LOGGER.info("list()" + engineerEntities);

        List<Engineer> engineerModels = engineerEntities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());
        return engineerModels;
    }

    public Engineer entityToModel(EngineerEntity engineerEntity) {
        LOGGER.info("entityToModel" + engineerEntity);
        ModelMapper modelMapper = new ModelMapper();
        Engineer engineerModel= modelMapper.map(engineerEntity, Engineer.class);
        return engineerModel;
    }

    public EngineerEntity modelToEntity(Engineer engineer) {
        LOGGER.info("modelToEntity()" + engineer);
        ModelMapper modelMapper = new ModelMapper();
        EngineerEntity engineerEntity = modelMapper.map(engineer, EngineerEntity.class);
        return engineerEntity;
    }
}
