package pl.medos.cmmsApi.service;

import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.repository.EmployeeRepository;

import java.util.List;
import java.util.logging.Logger;

@Service
public class EmployeeService {

    private static final Logger LOGGER = Logger.getLogger(EmployeeService.class.getName());

    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> list() {
        LOGGER.info("listEmployee()");
        List<Employee> employeeList = employeeRepository.findAll();
        LOGGER.info("listEmployee(...)");
        return employeeList;
    }

    public Employee create(Employee employee) {
        LOGGER.info("createEmployee(" + employee + ")");
        Employee createdEmployee = employeeRepository.save(employee);
        LOGGER.info("createEmployee(...) " + createdEmployee);
        return createdEmployee;
    }

    public Employee read(Long id) {
        LOGGER.info("Employee( " + id + " )");
        Employee readedEmployee = employeeRepository.findById(id).orElseThrow();
        LOGGER.info("Employee(...)" + readedEmployee);
        return readedEmployee;
    }

    public Employee update(Employee employee) {
        LOGGER.info("Employee( " + employee + " )");
        Employee editedEmployee = employeeRepository.findById(employee.getId()).orElseThrow();
        editedEmployee.setId(employee.getId());
        editedEmployee.setFirstName(employee.getFirstName());
        editedEmployee.setLastName(employee.getLastName());
        editedEmployee.setPosition(employee.getPosition());
        editedEmployee.setOccupation(employee.getOccupation());
        editedEmployee.setContact(employee.getContact());
        Employee updatedEmployee = employeeRepository.save(editedEmployee);
        LOGGER.info("Employee(...)" + updatedEmployee);
        return updatedEmployee;
    }

    public String delete(Long id) {
        LOGGER.info("Employee()");
        employeeRepository.deleteById(id);
        LOGGER.info("Employee()");
        return "Response: 200 Record "+id+"deleted!";
    }

}
