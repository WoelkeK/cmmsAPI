package pl.medos.cmmsApi.service;

import org.springframework.data.domain.Page;
import pl.medos.cmmsApi.exception.EmployeeNotFoundException;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.model.Engineer;

import java.util.List;

public interface EngineerService {

    List<Engineer> finadAllEngineers();

    Engineer createEngineer(Engineer engineer);

    Page<Engineer> findByActive(int pageNo, int pageSize, String sortField, String sortDir, Boolean profile);

    Engineer updateEngineer(Engineer engineer, Long id) throws EmployeeNotFoundException;

    void deleteEngineer(Long id);

    Page<Engineer> findEngineerByName(int pageNo, int pagesize, String query);

    List<Engineer> findEngineerByName(String engineerName);

    void deleteAll();

    Engineer findEngineerById(Long id) throws EmployeeNotFoundException;

    Page<Engineer> findPageinatedQuery(int pageNo, int pageSize, String sortField, String sortDir, String query);

    Page<Engineer> findPageinated(int pageNo, int pageSize, String sortField, String sortDir);

    Engineer findByName(String engineer);

}
