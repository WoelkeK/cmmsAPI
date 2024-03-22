package pl.medos.cmmsApi.service.mapper;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.repository.entity.DepartmentEntity;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DepartmentMapper {

    private DepartmentEntity departmentEntity;

    public List<Department> listModels(List<DepartmentEntity> departmentEntities) {
       log.debug("list()" + departmentEntities);

        List<Department> departmentModels = departmentEntities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());
        return departmentModels;
    }

    public Department entityToModel(DepartmentEntity departmentEntity) {
        log.debug("entityToModel" + departmentEntity);
        ModelMapper modelMapper = new ModelMapper();
        Department departmentModel = modelMapper.map(departmentEntity, Department.class);
        return departmentModel;
    }

    public DepartmentEntity modelToEntity(Department departmentModel) {
        log.debug("modelToEntity()" + departmentModel);
        ModelMapper modelMapper = new ModelMapper();
        DepartmentEntity departmentEntity = modelMapper.map(departmentModel, DepartmentEntity.class);
        return departmentEntity;
    }

    public Page<Department> mapEntitiesToModelsPage(Page<DepartmentEntity> departmentEntities) {
        log.debug("mapEntitiesToModelPage()");
        ModelMapper modelMapper = new ModelMapper();
        Page<Department> departmentPage = departmentEntities.map(DepartmentEntity -> modelMapper.map(DepartmentEntity, Department.class));
        log.debug("mapEntitiesToModelPage(...)");
        return departmentPage;
    }
}
