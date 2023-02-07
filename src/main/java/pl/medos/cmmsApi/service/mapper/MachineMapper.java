package pl.medos.cmmsApi.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Machine;
import pl.medos.cmmsApi.repository.entity.DepartmentEntity;
import pl.medos.cmmsApi.repository.entity.MachineEntity;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class MachineMapper {

    private static final Logger LOGGER = Logger.getLogger(MachineMapper.class.getName());

    private MachineEntity machineEntity;

    public List<Machine> listModels(List<MachineEntity> machineEntities) {
        LOGGER.info("list()" + machineEntities);

        List<Machine> machineModels = machineEntities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());
        return machineModels;
    }

    public Machine entityToModel(MachineEntity machineEntity) {
        LOGGER.info("entityToModel" + machineEntity);
        ModelMapper modelMapper = new ModelMapper();
        Machine machineModel = modelMapper.map(machineEntity, Machine.class);
        return machineModel;
    }

    public MachineEntity modelToEntity(Machine machineModel) {
        LOGGER.info("modelToEntity()" + machineModel);
        ModelMapper modelMapper = new ModelMapper();
        MachineEntity machineEntity = modelMapper.map(machineModel, MachineEntity.class);
        return machineEntity;
    }
}
