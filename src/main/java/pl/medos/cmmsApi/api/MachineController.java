package pl.medos.cmmsApi.api;

import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.MachineNotFoundException;
import pl.medos.cmmsApi.model.Machine;
import pl.medos.cmmsApi.service.impl.MachineServiceImpl;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class MachineController {

    private static final Logger LOGGER = Logger.getLogger(MachineController.class.getName());

    private MachineServiceImpl machineServiceImpl;

    public MachineController(MachineServiceImpl machineServiceImpl) {
        this.machineServiceImpl = machineServiceImpl;
    }

    @GetMapping("/machines")
    public List list() {
        LOGGER.info("machineList()");
        List machines = machineServiceImpl.findAllMachines();
        LOGGER.info("machineList(...)" + machines);
        return machines;
    }

    @PostMapping("/machine")
    public Machine create(@RequestBody Machine machine) {
        LOGGER.info("createMachine(" + machine + ")");
        Machine createdMachine = machineServiceImpl.createMachine(machine);
        LOGGER.info("createMachine(...)");
        return createdMachine;
    }

    @GetMapping("/machine/{id}")
    public Machine read(@PathVariable(name = "id") Long id) throws MachineNotFoundException {
        LOGGER.info("readMachine(" + id + ")");
        Machine readedMachine = machineServiceImpl.findMachineById(id);
        LOGGER.info("readMachine(...) " + readedMachine);
        return readedMachine;
    }

    @PutMapping("/machine")
    public Machine update(@RequestBody Machine machine) {
        LOGGER.info("updateMachine(" + machine + ")");
        Machine updatedMachine = machineServiceImpl.updateMachine(machine);
        LOGGER.info("updateMachine(...) " + updatedMachine);
        return updatedMachine;

    }

    @DeleteMapping("/machine/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        LOGGER.info("deleteMachine(" + id + ")");
        machineServiceImpl.deleteMachine(id);
        LOGGER.info("deleteMachine(...)");
    }
}
