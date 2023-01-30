package pl.medos.cmmsApi.service;

import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.model.Sensor;
import pl.medos.cmmsApi.repository.SensorRepository;

import java.util.List;
import java.util.logging.Logger;

@Service
public class SensorService {

    private static final Logger LOGGER = Logger.getLogger(SensorService.class.getName());

    private SensorRepository sensorRepository;

    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public List listAll() {
        LOGGER.info("listAll()");
        List<Sensor> sensorList = sensorRepository.findAll();
        LOGGER.info("ListAll(...)");
        return sensorList;

    }

    public Sensor create(Sensor sensor) {
        LOGGER.info("create(" + sensor + ")");
        Sensor createdSensor = sensorRepository.save(sensor);
        LOGGER.info("create(...)");
        return createdSensor;

    }

    public Sensor read(Long id) {
        LOGGER.info("read()");
        Sensor readedSensor = sensorRepository.findById(id).orElseThrow();
        LOGGER.info("read(...)");
        return readedSensor;

    }

    public Sensor update(Sensor sensor) {
        LOGGER.info("update()");
        Sensor editedSensor = sensorRepository.findById(sensor.getId()).orElseThrow();
        editedSensor.setIsConnected(sensor.getIsConnected());
        editedSensor.setName(sensor.getName());
        editedSensor.setReadings(sensor.getReadings());
        editedSensor.setStatus(sensor.getStatus());
        editedSensor.setType(sensor.getType());
        Sensor updatedSensor = sensorRepository.save(editedSensor);
        LOGGER.info("update(...) " + updatedSensor);
        return updatedSensor;
    }

    public String delete(Long id) {
        LOGGER.info("delete()");
        sensorRepository.deleteById(id);
        LOGGER.info("delete(...)");
        return "Record " + id + " deleted!";
    }
}
