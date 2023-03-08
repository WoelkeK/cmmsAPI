package pl.medos.cmmsApi.service.impl;

import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.exception.MachineNotFoundException;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Machine;
import pl.medos.cmmsApi.repository.MachineRepository;
import pl.medos.cmmsApi.repository.entity.JobEntity;
import pl.medos.cmmsApi.repository.entity.MachineEntity;
import pl.medos.cmmsApi.service.MachineService;
import pl.medos.cmmsApi.service.mapper.MachineMapper;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class MachineServiceImpl implements MachineService {

    private static final Logger LOGGER = Logger.getLogger(MachineServiceImpl.class.getName());

    private MachineRepository machineRepository;
    private MachineMapper machineMapper;

    public MachineServiceImpl(MachineRepository machineRepository, MachineMapper machineMapper) {
        this.machineRepository = machineRepository;
        this.machineMapper = machineMapper;
    }

    public List<Machine> findAllMachines() {

        LOGGER.info("list()");
        List<MachineEntity> mechineEntities = machineRepository.findAll();
        List<Machine> machineModels = machineMapper.listModels(mechineEntities);
        LOGGER.info("List(...)");
        return machineModels;
    }

    @Override
    public List<Machine> findMachinesByName(String name) {
        LOGGER.info("findMachinesByName()" + name);
        List<MachineEntity> machineEntities = machineRepository.searchMachineByName(name);
        List<Machine> machines = machineMapper.listModels(machineEntities);
        LOGGER.info("findMachinesByName(...)" + name);
        return machines;
    }

    @Override
    public List<Machine> findMachinesByDepartment(Department department) {
        LOGGER.info("findMachinesByName()" + department);
        List<MachineEntity> machineEntities = machineRepository.searchMachineByDepartment(department.getId());
        List<Machine> machines = machineMapper.listModels(machineEntities);
        LOGGER.info("findMachinesByName(...)" + department);
        return machines;
    }

    public Machine createMachine(Machine machine) {

        LOGGER.info("create(" + machine + ")");
        MachineEntity machineEntity = machineMapper.modelToEntity(machine);
        MachineEntity createdMachineEntity = machineRepository.save(machineEntity);
        Machine savedMachineModel = machineMapper.entityToModel(createdMachineEntity);
        LOGGER.info("create(...)" + savedMachineModel);
        return savedMachineModel;
    }

    public Machine findMachineById(Long id) throws MachineNotFoundException {
        LOGGER.info("read(" + id + ")");
        Optional<MachineEntity> optionalMachineEntity = machineRepository.findById(id);
        MachineEntity machineEntity = optionalMachineEntity.orElseThrow(
                () -> new MachineNotFoundException("Brak maszyny o podanym id " + id));
        Machine machineModel = machineMapper.entityToModel(machineEntity);
        LOGGER.info("read(...)" + machineModel);
        return machineModel;
    }

    public Machine updateMachine(Machine machine) {
        LOGGER.info("update()" + machine);
        MachineEntity machineEntity = machineMapper.modelToEntity(machine);
        MachineEntity updatedMachineEntity = machineRepository.save(machineEntity);
        Machine updatedMachineModel = machineMapper.entityToModel(updatedMachineEntity);
        LOGGER.info("update(...) " + updatedMachineModel);
        return updatedMachineModel;
    }

    public void deleteMachine(Long id) {
        LOGGER.info("delete()");
        machineRepository.deleteById(id);
        LOGGER.info("delete(...)");
    }
}
