package pl.medos.cmmsApi.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.repository.entity.DepartmentEntity;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class DepartmentMapper {

    private static final Logger LOGGER = Logger.getLogger(DepartmentMapper.class.getName());

    private DepartmentEntity departmentEntity;

    public List<Department> listModels(List<DepartmentEntity> departmentEntities) {
        LOGGER.info("list()" + departmentEntities);

        List<Department> departmentModels = departmentEntities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());
        return departmentModels;
    }

    public Department entityToModel(DepartmentEntity departmentEntity) {
        LOGGER.info("entityToModel" + departmentEntity);
        ModelMapper modelMapper = new ModelMapper();
        Department departmentModel = modelMapper.map(departmentEntity, Department.class);
        return departmentModel;
    }

    public DepartmentEntity modelToEntity(Department departmentModel) {
        LOGGER.info("modelToEntity()" + departmentModel);
        ModelMapper modelMapper = new ModelMapper();
        DepartmentEntity departmentEntity = modelMapper.map(departmentModel, DepartmentEntity.class);
        return departmentEntity;
    }

    public Page<Department> mapEntitiesToModelsPage(Page<DepartmentEntity> departmentEntities) {
        LOGGER.info("mapEntitiesToModelPage()");
        ModelMapper modelMapper = new ModelMapper();
        Page<Department> departmentPage = departmentEntities.map(DepartmentEntity -> modelMapper.map(DepartmentEntity, Department.class));
        LOGGER.info("mapEntitiesToModelPage(...)");
        return departmentPage;
    }
}
