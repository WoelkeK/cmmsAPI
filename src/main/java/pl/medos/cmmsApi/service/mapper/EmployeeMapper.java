package pl.medos.cmmsApi.service.mapper;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.repository.entity.EmployeeEntity;


import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
@Slf4j
public class EmployeeMapper {

    private EmployeeEntity employeeEntity;

    public List<Employee> listModels(List<EmployeeEntity> employeeEntities) {
        log.debug("list()" + employeeEntities);

        List<Employee> employeeModels = employeeEntities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());
        return employeeModels;
    }

    public Employee entityToModel(EmployeeEntity employeeEntity) {
        log.debug("entityToModel" + employeeEntity);
        ModelMapper modelMapper = new ModelMapper();
        Employee employeeModel = modelMapper.map(employeeEntity, Employee.class);
        return employeeModel;
    }

    public EmployeeEntity modelToEntity(Employee employeeModel) {
        log.debug("modelToEntity()" + employeeModel);
        ModelMapper modelMapper = new ModelMapper();
        EmployeeEntity employeeEntity = modelMapper.map(employeeModel, EmployeeEntity.class);
        return employeeEntity;
    }

    public Page<Employee> mapPageEntitiestoModels(Page<EmployeeEntity> employeeEntities) {
        log.debug("mapPageEntitiesToModels()");
        ModelMapper modelMapper = new ModelMapper();
        Page<Employee> employeePage = employeeEntities.map(EmployeeEntity -> modelMapper.map(EmployeeEntity, Employee.class));
        log.debug("mapPageEntitiesToModels(...)");
        return employeePage;
    }
}
