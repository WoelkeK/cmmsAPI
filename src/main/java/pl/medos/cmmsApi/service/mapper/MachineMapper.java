package pl.medos.cmmsApi.service.mapper;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Machine;
import pl.medos.cmmsApi.repository.entity.DepartmentEntity;
import pl.medos.cmmsApi.repository.entity.MachineEntity;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
@Slf4j
public class MachineMapper {

    private static final Logger LOGGER = Logger.getLogger(MachineMapper.class.getName());
    private MachineEntity machineEntity;

    public List<Machine> listModels(List<MachineEntity> machineEntities) {
        log.debug("list()" + machineEntities);

        List<Machine> machineModels = machineEntities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());
        return machineModels;
    }

    public Machine entityToModel(MachineEntity machineEntity) {
        log.debug("entityToModel" + machineEntity);
        ModelMapper modelMapper = new ModelMapper();
        Machine machineModel = modelMapper.map(machineEntity, Machine.class);
        return machineModel;
    }

    public MachineEntity modelToEntity(Machine machineModel) {
        log.debug("modelToEntity()" + machineModel);
        ModelMapper modelMapper = new ModelMapper();
        MachineEntity machineEntity = modelMapper.map(machineModel, MachineEntity.class);
        return machineEntity;
    }

    public Page<Machine> entititesToModelsPage(Page<MachineEntity> machineEntityPage) {
        log.debug("entitiesToModelsPage()");
        ModelMapper modelMapper = new ModelMapper();
        Page<Machine> machinePage = machineEntityPage.map(MachineEntity -> modelMapper.map(MachineEntity, Machine.class));
        log.debug("entitiesToModelsPage(...)");
        return machinePage;
    }
}
