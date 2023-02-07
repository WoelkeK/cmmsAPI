package pl.medos.cmmsApi.controllers;

import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.SensorNotFoundException;
import pl.medos.cmmsApi.model.Sensor;
import pl.medos.cmmsApi.service.SensorService;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class SensorController {

    private static final Logger LOGGER = Logger.getLogger(SensorController.class.getName());

    private SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping("/sensor")
    public List list() {
        LOGGER.info("sensorList()");
        List sensors = sensorService.list();
        LOGGER.info("sensorList(...)" + sensors);
        return sensors;
    }

    @PostMapping("/sensor")
    public Sensor create(@RequestBody Sensor sensor) {
        LOGGER.info("createSensor(" + sensor + ")");
        Sensor createdSensor = sensorService.create(sensor);
        LOGGER.info("createSensor(...)");
        return createdSensor;
    }

    @GetMapping("/sensor/{id}")
    public Sensor read(@PathVariable(name = "id") Long id) throws SensorNotFoundException {
        LOGGER.info("readSensor(" + id + ")");
        Sensor readedSensor = sensorService.read(id);
        LOGGER.info("readSensor(...) " + readedSensor);
        return readedSensor;
    }

    @PutMapping("/sensor")
    public Sensor update(@RequestBody Sensor sensor) {
        LOGGER.info("updateSensor(" + sensor + ")");
        Sensor updatedSensor = sensorService.update(sensor);
        LOGGER.info("updateSensor(...) " + updatedSensor);
        return updatedSensor;
    }

    @DeleteMapping("/sensor/{id}")
    public String delete(@PathVariable(name = "id") Long id) {
        LOGGER.info("deleteSensor(" + id + ")");
        String deleteMessage = sensorService.delete(id);
        LOGGER.info("deleteSensor(...)");
        return deleteMessage;
    }
}
