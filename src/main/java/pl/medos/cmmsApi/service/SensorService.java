//package pl.medos.cmmsApi.service;
//
//import org.springframework.stereotype.Service;
//import pl.medos.cmmsApi.exception.SensorNotFoundException;
//import pl.medos.cmmsApi.model.Sensor;
//import pl.medos.cmmsApi.repository.SensorRepository;
//import pl.medos.cmmsApi.repository.entity.SensorEntity;
//import pl.medos.cmmsApi.service.mapper.SensorMapper;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.logging.Logger;
//
//@Service
//public class SensorService {
//
//    private static final Logger LOGGER = Logger.getLogger(SensorService.class.getName());
//    private SensorRepository sensorRepository;
//    private SensorMapper sensorMapper;
//
//    public SensorService(SensorRepository sensorRepository, SensorMapper sensorMapper) {
//        this.sensorRepository = sensorRepository;
//        this.sensorMapper = sensorMapper;
//    }
//
//    public List list() {
//        LOGGER.info("list()");
//        List<SensorEntity> sensorEntities = sensorRepository.findAll();
//        List<Sensor> sensorModels = sensorMapper.listModels(sensorEntities);
//        LOGGER.info("list(...)");
//        return sensorModels;
//    }
//
//    public Sensor create(Sensor sensor) {
//        LOGGER.info("create(" + sensor + ")");
//        SensorEntity sensorEntity = sensorMapper.modelToEntity(sensor);
//        SensorEntity updatedSensorEntity = sensorRepository.save(sensorEntity);
//        Sensor updatedSensorModel = sensorMapper.entityToModel(updatedSensorEntity);
//        LOGGER.info("create(...)" + updatedSensorModel);
//        return updatedSensorModel;
//    }
//
//    public Sensor read(Long id) throws SensorNotFoundException {
//        LOGGER.info("read(" + id + ")");
//        Optional<SensorEntity> opotionalSensorEntity = sensorRepository.findById(id);
//        SensorEntity sensorEntity = opotionalSensorEntity.orElseThrow(() -> new SensorNotFoundException("Brak czujnika o podanym id " + id));
//        Sensor sensorModel = sensorMapper.entityToModel(sensorEntity);
//        LOGGER.info("read(...)" + sensorModel);
//        return sensorModel;
//    }
//
//    public Sensor update(Sensor sensor) {
//        LOGGER.info("update()" + sensor);
//        SensorEntity sensorEntity = sensorMapper.modelToEntity(sensor);
//        SensorEntity updatedSensorEntity = sensorRepository.save(sensorEntity);
//        Sensor updatedSensorModel = sensorMapper.entityToModel(updatedSensorEntity);
//        LOGGER.info("update(...) " + updatedSensorModel);
//        return updatedSensorModel;
//    }
//
//    public String delete(Long id) {
//        LOGGER.info("delete()");
//        sensorRepository.deleteById(id);
//        LOGGER.info("delete(...)");
//        return "Record " + id + " deleted!";
//    }
//}
