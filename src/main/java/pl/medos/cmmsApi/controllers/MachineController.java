package pl.medos.cmmsApi.controllers;

import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.model.Machine;
import pl.medos.cmmsApi.service.MachineService;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class MachineController {

    private static final Logger LOGGER = Logger.getLogger(MachineController.class.getName());

    private MachineService machineService;

    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    @GetMapping("/machine")
    public List list() {
        LOGGER.info("machineList()");
        List machines = machineService.listAll();
        LOGGER.info("machineList(...)" + machines);
        return machines;
    }

    @PostMapping("/machine")
    public Machine create(@RequestBody Machine machine) {
        LOGGER.info("createMachine(" + machine + ")");
        Machine createdMachine = machineService.create(machine);
        LOGGER.info("createMachine(...)");
        return createdMachine;
    }

    @GetMapping("/machine/{id}")
    public Machine read(@PathVariable(name = "id") Long id) {
        LOGGER.info("readMachine(" + id + ")");
        Machine readedMachine = machineService.read(id);
        LOGGER.info("readMachine(...) " + readedMachine);
        return readedMachine;
    }

    @PutMapping("/machine")
    public Machine update(@RequestBody Machine machine) {
        LOGGER.info("updateMachine(" + machine + ")");
        Machine updatedMachine = machineService.update(machine);
        LOGGER.info("updateMachine(...) " + updatedMachine);
        return updatedMachine;

    }

    @DeleteMapping("/machine/{id}")
    public String delete(@PathVariable(name = "id") Long id) {
        LOGGER.info("deleteMachine(" + id + ")");
        String deleteMessage = machineService.delete(id);
        LOGGER.info("deleteMachine(...)");
        return deleteMessage;
    }
}
