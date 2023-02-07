package pl.medos.cmmsApi.service;

import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.exception.MachineNotFoundException;
import pl.medos.cmmsApi.model.Machine;
import pl.medos.cmmsApi.repository.MachineRepository;
import pl.medos.cmmsApi.repository.entity.MachineEntity;
import pl.medos.cmmsApi.service.mapper.MachineMapper;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class MachineService {

    private static final Logger LOGGER = Logger.getLogger(MachineService.class.getName());

    private MachineRepository machineRepository;
    private MachineMapper machineMapper;

    public MachineService(MachineRepository machineRepository, MachineMapper machineMapper) {
        this.machineRepository = machineRepository;
        this.machineMapper = machineMapper;
    }

    public List list() {

        LOGGER.info("list()");
        List<MachineEntity> mechineEntities = machineRepository.findAll();
        List<Machine> machineModels = machineMapper.listModels(mechineEntities);
        LOGGER.info("List(...)");
        return machineModels;
    }

    public Machine create(Machine machine) {

        LOGGER.info("create(" + machine + ")");
        MachineEntity machineEntity = machineMapper.modelToEntity(machine);
        MachineEntity createdMachineEntity = machineRepository.save(machineEntity);
        Machine savedMachineModel = machineMapper.entityToModel(createdMachineEntity);
        LOGGER.info("create(...)" + savedMachineModel);
        return savedMachineModel;
    }

    public Machine read(Long id) throws MachineNotFoundException {
        LOGGER.info("read(" + id + ")");
        Optional<MachineEntity> optionalMachineEntity = machineRepository.findById(id);
        MachineEntity machineEntity = optionalMachineEntity.orElseThrow(
                () -> new MachineNotFoundException("Brak maszyny o podanym id " + id));
        Machine machineModel = machineMapper.entityToModel(machineEntity);
        LOGGER.info("read(...)" + machineModel);
        return machineModel;
    }

    public Machine update(Machine machine) {
        LOGGER.info("update()" + machine);
        MachineEntity machineEntity = machineMapper.modelToEntity(machine);
        MachineEntity updatedMachineEntity = machineRepository.save(machineEntity);
        Machine updatedMachineModel = machineMapper.entityToModel(updatedMachineEntity);
        LOGGER.info("update(...) " + updatedMachineModel);
        return updatedMachineModel;
    }

    public String delete(Long id) {
        LOGGER.info("delete()");
        machineRepository.deleteById(id);
        LOGGER.info("delete(...)");
        return "Response: 200 Record " + id + " deleted!";
    }
}
