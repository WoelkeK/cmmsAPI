package pl.medos.cmmsApi.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.exception.MachineNotFoundException;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Machine;
import pl.medos.cmmsApi.repository.MachineRepository;
import pl.medos.cmmsApi.repository.entity.DepartmentEntity;
import pl.medos.cmmsApi.repository.entity.MachineEntity;
import pl.medos.cmmsApi.service.MachineService;
import pl.medos.cmmsApi.service.mapper.MachineMapper;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@Slf4j
public class MachineServiceImpl implements MachineService {

    private MachineRepository machineRepository;
    private MachineMapper machineMapper;

    private List<Machine> sortedMachines;

    public MachineServiceImpl(MachineRepository machineRepository, MachineMapper machineMapper, List<Machine> sortedMachines) {
        this.machineRepository = machineRepository;
        this.machineMapper = machineMapper;
        this.sortedMachines = sortedMachines;
    }

    public List<Machine> findAllMachines() {

        log.debug("listAllMachines()");
        List<MachineEntity> mechineEntities = machineRepository.findAll();
        List<Machine> machineModels = machineMapper.listModels(mechineEntities);
        log.debug("ListAllMachines(...)");
        return machineModels;
    }

    @Override
    public List<Machine> findMachinesByDepartment(Department department) {
        log.debug("findMachinesByName()" + department);
        List<MachineEntity> machineEntities = machineRepository.searchMachineByDepartment(department.getId());
        sortedMachines = machineMapper.listModels(machineEntities);
        log.debug("findMachinesByName(...)" + department);
        return sortedMachines;
    }

    public Machine createMachine(Machine machine) {
        log.debug("create(" + machine + ")");
        MachineEntity machineEntity = machineMapper.modelToEntity(machine);
        MachineEntity createdMachineEntity = machineRepository.save(machineEntity);
        Machine savedMachineModel = machineMapper.entityToModel(createdMachineEntity);
        log.debug("create(...)" + savedMachineModel);
        return savedMachineModel;
    }

    public Machine findMachineById(Long id) throws MachineNotFoundException {
        log.debug("read(" + id + ")");
        Optional<MachineEntity> optionalMachineEntity = machineRepository.findById(id);
        MachineEntity machineEntity = optionalMachineEntity.orElseThrow(
                () -> new MachineNotFoundException("Brak maszyny o podanym id " + id));
        Machine machineModel = machineMapper.entityToModel(machineEntity);
        log.debug("read(...)" + machineModel);
        return machineModel;
    }

    public Machine updateMachine(Machine machine, Long id) throws MachineNotFoundException {
        log.debug("update()" + machine);
        Optional<MachineEntity> optionalMachineEntity = machineRepository.findById(id);
        MachineEntity machineEntity = optionalMachineEntity.orElseThrow(
                () -> new MachineNotFoundException("Brak maszyny o podanym id " + id));
        MachineEntity editedMachineEntity = machineMapper.modelToEntity(machine);
        MachineEntity updatedMachineEntity = machineRepository.save(editedMachineEntity);
        Machine updatedMachineModel = machineMapper.entityToModel(updatedMachineEntity);
        log.debug("update(...) " + updatedMachineModel);
        return updatedMachineModel;
    }

    public void deleteMachine(Long id) {
        log.debug("delete()");
        machineRepository.deleteById(id);
        log.debug("delete(...)");
    }

    @Override
    public Page<Machine> findPageinated(int pageNo, int size, String sortField, String sortDirection) {
        log.debug("findPaginated()");
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, size, sort);
        Page<MachineEntity> machineEntityPage = machineRepository.findAll(pageable);
        Page<Machine> machines = machineMapper.entititesToModelsPage(machineEntityPage);
        log.debug("findPaginated(...)");
        return machines;
    }

    @Override
    public void deleteAllMachine() {
        log.debug("deleteAllMachines");
        machineRepository.deleteAll();
    }

    @Override
    public Page<Machine> findPageinatedQuery(int pageNo, int pageSize, String sortField, String sortDir, String query) {
        log.debug("findPaginated()");
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        Page<MachineEntity> machineEntityPage = machineRepository.findByQueryPagable(query, pageable);
        Page<Machine> machines = machineMapper.entititesToModelsPage(machineEntityPage);
        log.debug("findPaginated(...)");
        return machines;
    }

    @Override
    public Machine findByName(String name) {
        log.debug("findByName" + name);
        List<MachineEntity> machineEntities = machineRepository.searchMachineByName(name);
        MachineEntity machineEntity = machineEntities.stream().findAny().orElse(new MachineEntity());
        Machine machine = machineMapper.entityToModel(machineEntity);
        log.debug("findByName(...)");
        return machine;
    }

    @Override
    public List<Machine> findMachinesByDepartmentId(Long id) {
        log.debug("findMachinesByDepartmentId()");
        DepartmentEntity department = new DepartmentEntity();
        department.setId(id);
        List<MachineEntity> machineEntities = machineRepository.findByDepartment(department);
        List<Machine> machines = machineMapper.listModels(machineEntities);
        log.debug("findMachinesByDepartmentId(...)");
        return machines;
    }
}
