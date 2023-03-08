package pl.medos.cmmsApi.service.impl;

import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.exception.EmployeeNotFoundException;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.repository.EmployeeRepository;
import pl.medos.cmmsApi.repository.entity.DepartmentEntity;
import pl.medos.cmmsApi.repository.entity.EmployeeEntity;
import pl.medos.cmmsApi.service.EmployeeService;
import pl.medos.cmmsApi.service.mapper.EmployeeMapper;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOGGER = Logger.getLogger(EmployeeServiceImpl.class.getName());

    private EmployeeRepository employeeRepository;
    private EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public List<Employee> finadAllEmployees() {
        LOGGER.info("findAllEmployees()");
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        List<Employee> employees = employeeMapper.listModels(employeeEntities);
        LOGGER.info("findAllEmployee(...)");
        return employees;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        LOGGER.info("createEmployee()");
        EmployeeEntity employeeEntity = employeeMapper.modelToEntity(employee);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
        Employee savedEmployeeModel = employeeMapper.entityToModel(savedEmployeeEntity);
        LOGGER.info("createEmployee(...) " + savedEmployeeModel);
        return savedEmployeeModel;
    }

    public Employee findEmployeeById(Long id) throws EmployeeNotFoundException {

        LOGGER.info("read( " + id + " )");
        Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepository.findById(id);
        EmployeeEntity employeeEntity = optionalEmployeeEntity.orElseThrow(
                () -> new EmployeeNotFoundException("Brak pracownika o podanym id " + id));
        Employee employeeModel = employeeMapper.entityToModel(employeeEntity);
        LOGGER.info("read(...)" + employeeModel);
        return employeeModel;
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        LOGGER.info("update( " + employee.getId() + " )");
        EmployeeEntity employeeEntity = employeeMapper.modelToEntity(employee);

        EmployeeEntity updatedEmployeeEntity = employeeRepository.save(employeeEntity);
        Employee updatedEmployeeModel = employeeMapper.entityToModel(updatedEmployeeEntity);
        LOGGER.info("update(...)" + updatedEmployeeModel.getId());
        return updatedEmployeeModel;
    }

    @Override
    public void deleteEmployee(Long id) {
        LOGGER.info("delete(" + id + ")");
        employeeRepository.deleteById(id);
        LOGGER.info("delete(...)");
    }

    @Override
    public Employee findEmployeeByName(String employeeName) {
        LOGGER.info("findEmployeeByName()" + employeeName);
        EmployeeEntity employeeEntity = employeeRepository.searchEmployeeByName(employeeName);
        Employee employee = employeeMapper.entityToModel(employeeEntity);
        LOGGER.info("findEmployeeByName(...)" + employee.getLastName());
        return employee;
    }
}
