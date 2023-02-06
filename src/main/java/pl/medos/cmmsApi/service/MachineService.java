//package pl.medos.cmmsApi.service;
//
//import org.springframework.stereotype.Service;
//import pl.medos.cmmsApi.model.Machine;
//import pl.medos.cmmsApi.repository.MachineRepository;
//
//import java.util.List;
//import java.util.logging.Logger;
//
//@Service
//public class MachineService {
//
//    private static final Logger LOGGER = Logger.getLogger(MachineService.class.getName());
//
//    private MachineRepository machineRepository;
//
//    public MachineService(MachineRepository machineRepository) {
//        this.machineRepository = machineRepository;
//    }
//
//    public List listAll() {
//        LOGGER.info("listAll()");
//        List<Machine> sensorList = machineRepository.findAll();
//        LOGGER.info("ListAll(...)");
//        return sensorList;
//
//    }
//
//    public Machine create(Machine machine) {
//        LOGGER.info("create(" + machine + ")");
//        Machine createdMachine = machineRepository.save(machine);
//        LOGGER.info("create(...)");
//        return createdMachine;
//
//    }
//
//    public Machine read(Long id) {
//        LOGGER.info("read()");
//        Machine readedMachine = machineRepository.findById(id).orElseThrow();
//        LOGGER.info("read(...)");
//        return readedMachine;
//
//    }
//
//    public Machine update(Machine machine) {
//        LOGGER.info("update()");
//        Machine editedMachine = machineRepository.findById(machine.getId()).orElseThrow();
//        editedMachine.setName(machine.getName());
//        editedMachine.setModel(machine.getModel());
//        editedMachine.setManufactureYear(machine.getManufactureYear());
//        editedMachine.setStatus(machine.getStatus());
//        editedMachine.setLocation(machine.getLocation());
//
//        Machine updatedMachine = machineRepository.save(editedMachine);
//        LOGGER.info("update(...) " + updatedMachine);
//        return updatedMachine;
//    }
//
//    public String delete(Long id) {
//        LOGGER.info("delete()");
//        machineRepository.deleteById(id);
//        LOGGER.info("delete(...)");
//        return "Record " + id + " deleted!";
//    }
//}
