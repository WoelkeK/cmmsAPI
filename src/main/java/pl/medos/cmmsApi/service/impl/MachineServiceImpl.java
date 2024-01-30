package pl.medos.cmmsApi.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.exception.MachineNotFoundException;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Machine;
import pl.medos.cmmsApi.repository.MachineRepository;
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


    private List<Machine> sortedMachines;

    public MachineServiceImpl(MachineRepository machineRepository, MachineMapper machineMapper, List<Machine> sortedMachines) {
        this.machineRepository = machineRepository;
        this.machineMapper = machineMapper;
        this.sortedMachines = sortedMachines;
    }

    public List<Machine> findAllMachines() {

        LOGGER.info("listAllMachines()");
        List<MachineEntity> mechineEntities = machineRepository.findAll();
        List<Machine> machineModels = machineMapper.listModels(mechineEntities);
        LOGGER.info("ListAllMachines(...)");
        return machineModels;
    }

    @Override
    public List<Machine> findMachinesByName(String name) {
        LOGGER.info("findMachinesByName()" + name);
        List<MachineEntity> machineEntities = machineRepository.searchMachineByName(name);
        sortedMachines = machineMapper.listModels(machineEntities);
        LOGGER.info("findMachinesByName(...)" + name);
        return sortedMachines;
    }

    @Override
    public List<Machine> findMachinesByDepartment(Department department) {
        LOGGER.info("findMachinesByName()" + department);
        List<MachineEntity> machineEntities = machineRepository.searchMachineByDepartment(department.getId());
        sortedMachines = machineMapper.listModels(machineEntities);
        LOGGER.info("findMachinesByName(...)" + department);
        return sortedMachines;
    }

    @Override
    public List<Machine> findMachinesByQuery(String query) {
        LOGGER.info("findMachinesByQuery()" + query);
        List<MachineEntity> machineEntities = machineRepository.searchMachineByQuery(query);
        List<Machine> machines = machineMapper.listModels(machineEntities);
        LOGGER.info("findMachinesByQuery(...)");
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

    public Machine updateMachine(Machine machine, Long id) throws MachineNotFoundException {
        LOGGER.info("update()" + machine);
        Optional<MachineEntity> optionalMachineEntity = machineRepository.findById(id);
        MachineEntity machineEntity = optionalMachineEntity.orElseThrow(
                () -> new MachineNotFoundException("Brak maszyny o podanym id " + id));

        MachineEntity editedMachineEntity = machineMapper.modelToEntity(machine);
        MachineEntity updatedMachineEntity = machineRepository.save(editedMachineEntity);
        Machine updatedMachineModel = machineMapper.entityToModel(updatedMachineEntity);
        LOGGER.info("update(...) " + updatedMachineModel);
        return updatedMachineModel;
    }

    public void deleteMachine(Long id) {
        LOGGER.info("delete()");
        machineRepository.deleteById(id);
        LOGGER.info("delete(...)");
    }

    @Override
    public Page<Machine> findPageinated(int pageNo, int size, String sortField, String sortDirection) {
        LOGGER.info("findPaginated()");
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, size, sort);
        Page<MachineEntity> machineEntityPage = machineRepository.findAll(pageable);
        Page<Machine> machines = machineMapper.entititesToModelsPage(machineEntityPage);
        LOGGER.info("findPaginated(...)");
        return machines;
    }

    @Override
    public void deleteAllMachine() {
        LOGGER.info("deleteAllMachines");
        machineRepository.deleteAll();
    }

    @Override
    public Page<Machine> findPageinatedQuery(int pageNo, int pageSize, String sortField, String sortDir, String query) {
        LOGGER.info("findPaginated()");
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        Page<MachineEntity> machineEntityPage = machineRepository.findByQueryPagable(query, pageable);
//        Page<MachineEntity> machineEntityPage = machineRepository.findAll(pageable);
        Page<Machine> machines = machineMapper.entititesToModelsPage(machineEntityPage);
        LOGGER.info("findPaginated(...)");
        return machines;
    }

    @Override
    public Machine findByName(String name) {
        LOGGER.info("findByName" + name);
        List<MachineEntity> machineEntities = machineRepository.searchMachineByName(name);
        MachineEntity machineEntity = machineEntities.stream().findAny().orElse(new MachineEntity());
        Machine machine = machineMapper.entityToModel(machineEntity);
        LOGGER.info("findByName(...)");
        return machine;
    }
}
