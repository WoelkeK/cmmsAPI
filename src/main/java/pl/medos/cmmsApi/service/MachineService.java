package pl.medos.cmmsApi.service;

import pl.medos.cmmsApi.exception.MachineNotFoundException;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Machine;
import pl.medos.cmmsApi.repository.entity.MachineEntity;

import java.util.List;

public interface MachineService {

    List<Machine> findAllMachines();

    List<Machine> findMachinesByName(String name);
    List<Machine> findMachinesByDepartment(Department department);

    Machine createMachine(Machine machine);

    Machine findMachineById(Long id) throws MachineNotFoundException;

    Machine updateMachine(Machine machine);

    void deleteMachine(Long id);

    List<MachineEntity> getThisListMachine();
}

