package pl.medos.cmmsApi.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.medos.cmmsApi.model.Sensor;
import pl.medos.cmmsApi.model.User;
import pl.medos.cmmsApi.repository.entity.SensorEntity;
import pl.medos.cmmsApi.repository.entity.UserEntity;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class SensorMapper {

    private static final Logger LOGGER = Logger.getLogger(SensorMapper.class.getName());

    private UserEntity userEntity;

    public List<Sensor> listModels(List<SensorEntity> sensorEntities) {
        LOGGER.info("list()" + sensorEntities);
        List<Sensor> sensorModels = sensorEntities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());
        return sensorModels;
    }

    public Sensor entityToModel(SensorEntity sensorEntity) {
        LOGGER.info("entityToModel" + sensorEntity);
        ModelMapper modelMapper = new ModelMapper();
        Sensor sensorModel= modelMapper.map(sensorEntity, Sensor.class);
        return sensorModel;
    }

    public SensorEntity modelToEntity(Sensor sensor) {
        LOGGER.info("modelToEntity()" + sensor);
        ModelMapper modelMapper = new ModelMapper();
        SensorEntity sensorEntity = modelMapper.map(sensor, SensorEntity.class);
        return sensorEntity;
    }
}
