package pl.medos.cmmsApi.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import pl.medos.cmmsApi.model.Hardware;
import pl.medos.cmmsApi.model.Job;
import pl.medos.cmmsApi.repository.entity.HardwareEntity;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class HardwareMapper {

    private static final Logger LOGGER = Logger.getLogger(HardwareMapper.class.getName());

    public Hardware mapEntityToModel(HardwareEntity hardwareEntity) {
        LOGGER.info("mapEntityToModel()");
        ModelMapper modelMapper = new ModelMapper();
        Hardware hardware = modelMapper.map(hardwareEntity, Hardware.class);
        LOGGER.info("mapEntityToModel(...)");
        return hardware;
    }

    public HardwareEntity mapModelToEntity(Hardware hardware) {
        LOGGER.info("mapModelToEntity()");
        ModelMapper modelMapper = new ModelMapper();
        HardwareEntity hardwareEntity = modelMapper.map(hardware, HardwareEntity.class);
        LOGGER.info("mapModelToEntity(...)");
        return hardwareEntity;
    }

    public List<Hardware> litsEntityToModels(List<HardwareEntity> hardwareEntities) {
        LOGGER.info("listEntityToModel()");
        ModelMapper modelMapper = new ModelMapper();
        List<Hardware> hardwares = hardwareEntities.stream()
                .map(this::mapEntityToModel)
                .toList();
        LOGGER.info("listEntityToModel(...)");
        return hardwares;
    }

    public Page<Hardware> pageEntityToModels(Page<HardwareEntity> hardwareEntities){
        LOGGER.info("pageEntityToModels()");
        ModelMapper modelMapper = new ModelMapper();
        Page<Hardware> hardwarePage = hardwareEntities.map(HardwareEntity -> modelMapper.map(HardwareEntity, Hardware.class));
        LOGGER.info("pageEntityToModels(...)");
        return hardwarePage;
    }
}
