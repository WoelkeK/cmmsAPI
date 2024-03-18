package pl.medos.cmmsApi.service;

import org.springframework.data.domain.Page;
import pl.medos.cmmsApi.exception.MachineNotFoundException;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Machine;

import java.util.List;

public interface MachineService {

    List<Machine> findAllMachines();
    List<Machine> findMachinesByDepartment(Department department);

    Machine createMachine(Machine machine);

    Machine findMachineById(Long id) throws MachineNotFoundException;

    Machine updateMachine(Machine machine, Long id) throws MachineNotFoundException;

    void deleteMachine(Long id);

    Page<Machine> findPageinated(int pageNo, int size, String sortField, String sortDirection);

    void deleteAllMachine();

    Page<Machine> findPageinatedQuery(int pageNo, int pageSize, String sortField, String sortDir, String query);

    Machine findByName(String machine);

    List<Machine> findMachinesByDepartmentId(Long id);
}

