package pl.medos.cmmsApi.service;

import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.exception.EmployeeNotFoundException;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.repository.EmployeeRepository;
import pl.medos.cmmsApi.repository.entity.EmployeeEntity;
import pl.medos.cmmsApi.service.mapper.EmployeeMapper;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class EmployeeService {

    private static final Logger LOGGER = Logger.getLogger(EmployeeService.class.getName());

    private EmployeeRepository employeeRepository;
    private EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    public List<Employee> list() {
        LOGGER.info("list()");
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        List<Employee> employees = employeeMapper.listModels(employeeEntities);
        LOGGER.info("list(...)");
        return employees;
    }

    public Employee create(Employee employee) {
        LOGGER.info("create(" + employee + ")");
        EmployeeEntity employeeEntity = employeeMapper.modelToEntity(employee);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
        Employee savedEmployeeModel = employeeMapper.entityToModel(savedEmployeeEntity);
        LOGGER.info("create(...) " + savedEmployeeModel);
        return savedEmployeeModel;
    }

    public Employee read(Long id) throws EmployeeNotFoundException {

        LOGGER.info("read( " + id + " )");
        Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepository.findById(id);
        EmployeeEntity employeeEntity = optionalEmployeeEntity.orElseThrow(
                () -> new EmployeeNotFoundException("Brak pracownika o podanym id " + id));
        Employee employeeModel = employeeMapper.entityToModel(employeeEntity);
        LOGGER.info("read(...)" + employeeModel);
        return employeeModel;
    }

    public Employee update(Employee employee) {

        LOGGER.info("update( " + employee + " )");
        EmployeeEntity employeeEntity = employeeMapper.modelToEntity(employee);
        EmployeeEntity updatedEmployeeEntity = employeeRepository.save(employeeEntity);
        Employee updatedEmployeeModel = employeeMapper.entityToModel(updatedEmployeeEntity);
        LOGGER.info("update()" + updatedEmployeeModel);
        return updatedEmployeeModel;
    }

//    public Employee update(Employee employee) {
//        LOGGER.info("update( " + employee + " )");
//        Employee editedEmployee = employeeRepository.findById(employee.getId()).orElseThrow();
//        editedEmployee.setId(employee.getId());
//        editedEmployee.setFirstName(employee.getFirstName());
//        editedEmployee.setLastName(employee.getLastName());
//        editedEmployee.setPosition(employee.getPosition());
//        editedEmployee.setOccupation(employee.getOccupation());
//        Employee updatedEmployee = employeeRepository.save(editedEmployee);
//        LOGGER.info("Employee(...)" + updatedEmployee);
//        return updatedEmployee;
//    }

    public String delete(Long id) {
        LOGGER.info("delete(" + id + ")");
        employeeRepository.deleteById(id);
        LOGGER.info("delete(...)");
        return "Response: 200 Record " + id + "deleted!";
    }
}
