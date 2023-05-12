package pl.medos.cmmsApi.service;

import org.springframework.data.domain.Page;
import pl.medos.cmmsApi.exception.MachineNotFoundException;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Machine;

import java.util.List;

public interface MachineService {

    List<Machine> findAllMachines();

    List<Machine> findMachinesByName(String name);

    List<Machine> findMachinesByDepartment(Department department);

    List<Machine> findMachinesByQuery(String query);

    Machine createMachine(Machine machine);

    Machine findMachineById(Long id) throws MachineNotFoundException;

    Machine updateMachine(Machine machine, Long id) throws MachineNotFoundException;

    void deleteMachine(Long id);

    Page<Machine> findPageinated(int pageNo, int size);
}

