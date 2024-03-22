package pl.medos.cmmsApi.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.MachineNotFoundException;
import pl.medos.cmmsApi.model.Machine;
import pl.medos.cmmsApi.service.impl.MachineServiceImpl;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/machines")
@Slf4j
public class MachineController {

    private MachineServiceImpl machineServiceImpl;

    public MachineController(MachineServiceImpl machineServiceImpl) {
        this.machineServiceImpl = machineServiceImpl;
    }

    @GetMapping
    public List list() {
        log.debug("machineList()");
        List machines = machineServiceImpl.findAllMachines();
        log.debug("machineList(...)" + machines);
        return machines;
    }

    @PostMapping("/create")
    public Machine create(@RequestBody Machine machine) {
        log.debug("createMachine(" + machine + ")");
        Machine createdMachine = machineServiceImpl.createMachine(machine);
        log.debug("createMachine(...)");
        return createdMachine;
    }

    @GetMapping("/read/{id}")
    public Machine read(@PathVariable(name = "id") Long id) throws MachineNotFoundException {
        log.debug("readMachine(" + id + ")");
        Machine readedMachine = machineServiceImpl.findMachineById(id);
        log.debug("readMachine(...) " + readedMachine);
        return readedMachine;
    }

    @PutMapping("/update/{id}")
    public Machine update(@PathVariable(name = "id") Long id,
                          @RequestBody Machine machine) throws MachineNotFoundException {
        log.debug("updateMachine(" + machine + ")");
        Machine updatedMachine = machineServiceImpl.updateMachine(machine, id);
        log.debug("updateMachine(...) " + updatedMachine);
        return updatedMachine;
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        log.debug("deleteMachine(" + id + ")");
        machineServiceImpl.deleteMachine(id);
        log.debug("deleteMachine(...)");
    }

    @GetMapping("byDepartment")
    public List<Machine> findByDepartment(@RequestParam(name = "departmentId") Long id) {
        log.debug("findByDepartmentId()");
        List<Machine> machinesByDepartment = machineServiceImpl.findMachinesByDepartmentId(id);
        log.debug("findByDepartment(...)");
        return machinesByDepartment;
    }
}
