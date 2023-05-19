package pl.medos.cmmsApi.service.mapper;

import org.springframework.stereotype.Component;
import pl.medos.cmmsApi.model.Hardware;
import pl.medos.cmmsApi.repository.entity.HardwareEntity;

import java.util.List;
import java.util.logging.Logger;

@Component
public class HardwareMapper {

    private static final Logger LOGGER = Logger.getLogger(HardwareMapper.class.getName());

    public Hardware mapEntityToModel(HardwareEntity hardwareEntity) {
        return null;
    }

    public HardwareEntity mapModelToEntity(Hardware hardware) {
        return null;
    }

    public List<Hardware> litsEntityToModels(List<HardwareEntity> hardwareEntities) {
        return null;
    }

}
