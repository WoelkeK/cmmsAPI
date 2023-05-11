package pl.medos.cmmsApi.service.mapper;

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
public class EmployeeMapper {

    private static final Logger LOGGER = Logger.getLogger(EmployeeMapper.class.getName());

    private EmployeeEntity employeeEntity;

    public List<Employee> listModels(List<EmployeeEntity> employeeEntities) {
        LOGGER.info("list()" + employeeEntities);

        List<Employee> employeeModels = employeeEntities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());
        return employeeModels;
    }


    public Employee entityToModel(EmployeeEntity employeeEntity) {
        LOGGER.info("entityToModel" + employeeEntity);
        ModelMapper modelMapper = new ModelMapper();
        Employee employeeModel = modelMapper.map(employeeEntity, Employee.class);
//        employeeModel.setPassword(null);
        return employeeModel;
    }

    public EmployeeEntity modelToEntity(Employee employeeModel) {
        LOGGER.info("modelToEntity()" + employeeModel);
        ModelMapper modelMapper = new ModelMapper();
        EmployeeEntity employeeEntity = modelMapper.map(employeeModel, EmployeeEntity.class);
        return employeeEntity;
    }
    public Page<Employee> mapPageEntitiestoModels(Page<EmployeeEntity> employeeEntities) {
        LOGGER.info("mapPageEntitiesToModels()");
        ModelMapper modelMapper = new ModelMapper();
        Page<Employee> employeePage = employeeEntities.map(EmployeeEntity -> modelMapper.map(EmployeeEntity, Employee.class));
        LOGGER.info("mapPageEntitiesToModels(...)");
        return employeePage;
    }
}
