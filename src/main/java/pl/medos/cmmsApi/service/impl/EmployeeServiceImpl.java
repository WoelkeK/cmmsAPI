package pl.medos.cmmsApi.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Employee updateEmployee(Employee employee, Long id) throws EmployeeNotFoundException {
        LOGGER.info("update( " + employee.getId() + " )");
        Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepository.findById(id);
        EmployeeEntity employeeEntity = optionalEmployeeEntity.orElseThrow(
                () -> new EmployeeNotFoundException("Brak pracownika o podanym id " + id));

        EmployeeEntity editedEmployeeEntity = employeeMapper.modelToEntity(employee);
        editedEmployeeEntity.setId(employeeEntity.getId());
        EmployeeEntity updatedEmployeeEntity = employeeRepository.save(editedEmployeeEntity);
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
    public List<Employee> findEmployeeByName(String employeeName) {
        LOGGER.info("findEmployeeByName()" + employeeName);
        List<EmployeeEntity> employeeEntities = employeeRepository.searchEmployeeByName(employeeName);

        List<Employee> employees = employeeMapper.listModels(employeeEntities);
        LOGGER.info("findEmployeeByName(...)");
        return employees;
    }

    @Override
    public Page<Employee> findPageinated(int pageNo, int pagesize) {
        LOGGER.info("findPageinated()");
        Pageable pageable = PageRequest.of(pageNo-1, pagesize);
        Page<EmployeeEntity> employeeEntityPage = employeeRepository.findAll(pageable);
        Page<Employee> employeePage = employeeMapper.mapPageEntitiestoModels(employeeEntityPage);
        LOGGER.info("findPageinated(...)");
        return employeePage;
    }
}
