package pl.medos.cmmsApi.controllers;

import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/test")
    public String apiTest() {
        LOGGER.info("test()");
        return "test ok! :Response 200";
    }

    @GetMapping("/sensors")
    public List allSensorsList() {
        LOGGER.info("allSensorsList()");
        List allSensors = sensorService.listAll();
        LOGGER.info("allSensorsList(...)" + allSensors);
        return allSensors;
    }

    @PostMapping("/sensor")
    public Sensor createSensor(@RequestBody Sensor sensor) {
        LOGGER.info("createSensor(" + sensor + ")");
        Sensor createdSensor = sensorService.create(sensor);
        LOGGER.info("createSensor(...)");
        return createdSensor;
    }

    @GetMapping("/sensor/{id}")
    public Sensor readSensor(@PathVariable(name = "id") Long id) {
        LOGGER.info("readSensor(" + id + ")");
        Sensor readedSensor = sensorService.read(id);
        LOGGER.info("readSensor(...) " + readedSensor);
        return readedSensor;
    }

    @PutMapping("/sensor")
    public Sensor updateSensor(@RequestBody Sensor sensor) {
        LOGGER.info("updateSensor(" + sensor + ")");
        Sensor updatedSensor = sensorService.update(sensor);
        LOGGER.info("updateSensor(...) " + updatedSensor);
        return updatedSensor;

    }

    @DeleteMapping("/sensor/{id}")
    public String deleteSensor(@PathVariable(name = "id") Long id) {
        LOGGER.info("deleteSensor(" + id + ")");
        String deleteMessage = sensorService.delete(id);
        LOGGER.info("deleteSensor(...)");
        return deleteMessage;
    }
}
