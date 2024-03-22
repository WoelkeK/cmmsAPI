package pl.medos.cmmsApi.service.mapper;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class HardwareMapper {

    public Hardware mapEntityToModel(HardwareEntity hardwareEntity) {
        log.debug("mapEntityToModel()");
        ModelMapper modelMapper = new ModelMapper();
        Hardware hardware = modelMapper.map(hardwareEntity, Hardware.class);
        log.debug("mapEntityToModel(...)");
        return hardware;
    }

    public HardwareEntity mapModelToEntity(Hardware hardware) {
        log.debug("mapModelToEntity()");
        ModelMapper modelMapper = new ModelMapper();
        HardwareEntity hardwareEntity = modelMapper.map(hardware, HardwareEntity.class);
        log.debug("mapModelToEntity(...)");
        return hardwareEntity;
    }

    public List<Hardware> litsEntityToModels(List<HardwareEntity> hardwareEntities) {
        log.debug("listEntityToModel()");
        ModelMapper modelMapper = new ModelMapper();
        List<Hardware> hardwares = hardwareEntities.stream()
                .map(this::mapEntityToModel)
                .toList();
        log.debug("listEntityToModel(...)");
        return hardwares;
    }

    public Page<Hardware> pageEntityToModels(Page<HardwareEntity> hardwareEntities) {
        log.debug("pageEntityToModels()");
        ModelMapper modelMapper = new ModelMapper();
        Page<Hardware> hardwarePage = hardwareEntities.map(HardwareEntity -> modelMapper.map(HardwareEntity, Hardware.class));
        log.debug("pageEntityToModels(...)");
        return hardwarePage;
    }
}
