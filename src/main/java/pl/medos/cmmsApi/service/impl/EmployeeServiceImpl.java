package pl.medos.cmmsApi.service.impl;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public List<Employee> finadAllEmployees() {
        log.debug("findAllEmployees()");
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        List<Employee> employees = employeeMapper.listModels(employeeEntities);
        log.debug("findAllEmployee(...)");
        return employees;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        log.debug("createEmployee()");
        employee.setIsActive(true);
        EmployeeEntity employeeEntity = employeeMapper.modelToEntity(employee);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
        Employee savedEmployeeModel = employeeMapper.entityToModel(savedEmployeeEntity);
        log.debug("createEmployee(...) " + savedEmployeeModel);
        return savedEmployeeModel;
    }

    public Employee findEmployeeById(Long id) throws EmployeeNotFoundException {

        log.debug("read( " + id + " )");
        Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepository.findById(id);
        EmployeeEntity employeeEntity = optionalEmployeeEntity.orElseThrow(
                () -> new EmployeeNotFoundException("Brak pracownika o podanym id " + id));
        Employee employeeModel = employeeMapper.entityToModel(employeeEntity);
        log.debug("read(...)" + employeeModel);
        return employeeModel;
    }

    @Override
    public Employee updateEmployee(Employee employee, Long id) throws EmployeeNotFoundException {
        log.debug("update( " + employee.getId() + " )");
        Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepository.findById(id);
        EmployeeEntity employeeEntity = optionalEmployeeEntity.orElseThrow(
                () -> new EmployeeNotFoundException("Brak pracownika o podanym id " + id));

        EmployeeEntity editedEmployeeEntity = employeeMapper.modelToEntity(employee);
        editedEmployeeEntity.setId(employeeEntity.getId());
        EmployeeEntity updatedEmployeeEntity = employeeRepository.save(editedEmployeeEntity);
        Employee updatedEmployeeModel = employeeMapper.entityToModel(updatedEmployeeEntity);
        log.debug("update(...)" + updatedEmployeeModel.getId());
        return updatedEmployeeModel;
    }

    @Override
    public void deleteEmployee(Long id) {
        log.debug("delete(" + id + ")");
        employeeRepository.deleteById(id);
        log.debug("delete(...)");
    }

    @Override
    public Page<Employee> findEmployeeByName(int pageNo, int pagesize, String employeeName) {
        log.debug("findEmployeeByName()" + employeeName);
        Pageable pageable = PageRequest.of(pageNo-1, pagesize);
        Page<EmployeeEntity> employeeEntities = employeeRepository.searchEmployeeByQuery(pageable,employeeName);
        Page<Employee> employees = employeeMapper.mapPageEntitiestoModels(employeeEntities);
        log.debug("findEmployeeByName(...)");
        return employees;
    }

    @Override
    public List<Employee> findEmployeeByRawName(String employeeName) {
        log.debug("findEmployeeByName()" + employeeName);
        List<EmployeeEntity> employeeEntities = employeeRepository.searchEmployeeByRawQuery(employeeName);
        List<Employee> employees = employeeMapper.listModels(employeeEntities);
        log.debug("findEmployeeByName(...)");
        return employees;
    }

    @Override
    public Page<Employee> findPageinated(int pageNo, int pagesize, String sortField, String sortDir) {
        log.debug("findPageinated()");
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pagesize,sort);
        Page<EmployeeEntity> employeeEntityPage = employeeRepository.findAll(pageable);
        Page<Employee> employeePage = employeeMapper.mapPageEntitiestoModels(employeeEntityPage);
        log.debug("findPageinated(...)");
        return employeePage;
    }

    @Override
    public Page<Employee> findPageinatedQuery(int pageNo, int pageSize, String sortField, String sortDir, String query) {
        log.debug("findPaginated()");
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        Page<EmployeeEntity> employeeEntityPage = employeeRepository.findByQueryPagable(query, pageable);
        Page<Employee> employees = employeeMapper.mapPageEntitiestoModels(employeeEntityPage);
        log.debug("findPaginated(...)");
        return employees;
    }

    @Override
    public void deleteAll() {
        log.debug("deleteAll");
        employeeRepository.deleteAll();
    }

    @Override
    public Employee findByEmployee(String employeeName) {
        log.debug("findEmployeeByName()" + employeeName);
        EmployeeEntity byEmployee = employeeRepository.findByName(employeeName);
        Employee employee = employeeMapper.entityToModel(byEmployee);
        log.debug("findEmployeeByName(...)");
        return employee;
    }
}
