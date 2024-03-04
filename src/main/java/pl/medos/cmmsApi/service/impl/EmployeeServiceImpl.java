package pl.medos.cmmsApi.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.exception.EmployeeNotFoundException;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.repository.EmployeeRepository;
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
        employee.setIsActive(true);
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
    public Page<Employee> findEmployeeByName(int pageNo, int pagesize, String employeeName) {
        LOGGER.info("findEmployeeByName()" + employeeName);
        Pageable pageable = PageRequest.of(pageNo-1, pagesize);
        Page<EmployeeEntity> employeeEntities = employeeRepository.searchEmployeeByQuery(pageable,employeeName);
        Page<Employee> employees = employeeMapper.mapPageEntitiestoModels(employeeEntities);
        LOGGER.info("findEmployeeByName(...)");
        return employees;
    }

    @Override
    public List<Employee> findEmployeeByRawName(String employeeName) {
        LOGGER.info("findEmployeeByName()" + employeeName);
        List<EmployeeEntity> employeeEntities = employeeRepository.searchEmployeeByRawQuery(employeeName);
        List<Employee> employees = employeeMapper.listModels(employeeEntities);
        LOGGER.info("findEmployeeByName(...)");
        return employees;
    }

    @Override
    public Page<Employee> findPageinated(int pageNo, int pagesize, String sortField, String sortDir) {
        LOGGER.info("findPageinated()");
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pagesize,sort);
        Page<EmployeeEntity> employeeEntityPage = employeeRepository.findAll(pageable);
        Page<Employee> employeePage = employeeMapper.mapPageEntitiestoModels(employeeEntityPage);
        LOGGER.info("findPageinated(...)");
        return employeePage;
    }

    @Override
    public Page<Employee> findPageinatedQuery(int pageNo, int pageSize, String sortField, String sortDir, String query) {
        LOGGER.info("findPaginated()");
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        Page<EmployeeEntity> employeeEntityPage = employeeRepository.findByQueryPagable(query, pageable);
        Page<Employee> employees = employeeMapper.mapPageEntitiestoModels(employeeEntityPage);
        LOGGER.info("findPaginated(...)");
        return employees;
    }

    @Override
    public void deleteAll() {
        LOGGER.info("deleteAll");
        employeeRepository.deleteAll();
    }

    @Override
    public Employee findByEmployee(String employeeName) {
        LOGGER.info("findEmployeeByName()" + employeeName);
        EmployeeEntity byEmployee = employeeRepository.findByName(employeeName);
        Employee employee = employeeMapper.entityToModel(byEmployee);
        LOGGER.info("findEmployeeByName(...)");
        return employee;
    }
}
