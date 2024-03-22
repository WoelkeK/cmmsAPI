package pl.medos.cmmsApi.service.mapper;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.model.Engineer;
import pl.medos.cmmsApi.repository.entity.EmployeeEntity;
import pl.medos.cmmsApi.repository.entity.EngineerEntity;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
@Slf4j
public class EngineerMapper {

    private EngineerEntity engineerEntity;

    public List<Engineer> listModels(List<EngineerEntity> engineerEntities ){
        log.debug("list()" + engineerEntities);

        List<Engineer> engineerModels = engineerEntities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());
        return engineerModels;
    }

    public Engineer entityToModel(EngineerEntity engineerEntity) {
        log.debug("entityToModel" + engineerEntity);
        ModelMapper modelMapper = new ModelMapper();
        Engineer engineerModel= modelMapper.map(engineerEntity, Engineer.class);
        return engineerModel;
    }

    public EngineerEntity modelToEntity(Engineer engineer) {
        log.debug("modelToEntity()" + engineer);
        ModelMapper modelMapper = new ModelMapper();
        EngineerEntity engineerEntity = modelMapper.map(engineer, EngineerEntity.class);
        return engineerEntity;
    }

    public Page<Engineer> mapPageEntitiestoModels(Page<EngineerEntity> engineerEntityPage) {
        log.debug("mapPageEntitiesToModels()");
        ModelMapper modelMapper = new ModelMapper();
        Page<Engineer> engineerPage = engineerEntityPage.map(EngineerEntity -> modelMapper.map(EngineerEntity, Engineer.class));
        log.debug("mapPageEntitiesToModels(...)");
        return engineerPage;

    }
}
