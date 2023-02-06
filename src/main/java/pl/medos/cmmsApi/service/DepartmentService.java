package pl.medos.cmmsApi.service;

import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.exception.DepartmentNotFoundException;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.repository.DepartmentRepository;
import pl.medos.cmmsApi.repository.entity.DepartmentEntity;
import pl.medos.cmmsApi.service.mapper.DepartmentMapper;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class DepartmentService {

    private static final Logger LOGGER = Logger.getLogger(DepartmentService.class.getName());

    private DepartmentRepository departmentRepository;
    private DepartmentMapper departmentMapper;

    public DepartmentService(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    public List list() {
        LOGGER.info("list()");
        List<DepartmentEntity> departmentEntities = departmentRepository.findAll();
        List<Department> departmentModels = departmentMapper.listModels(departmentEntities);
        LOGGER.info("list(...)");
        return departmentModels;
    }

    public Department create(Department department) {
        LOGGER.info("create(" + department + ")");
        DepartmentEntity departmentEntity = departmentMapper.modelToEntity(department);
        DepartmentEntity savedDepartmentEntity = departmentRepository.save(departmentEntity);
        Department savedDepartmentModel = departmentMapper.entityToModel(savedDepartmentEntity);
        LOGGER.info("create(...)" + savedDepartmentModel);
        return savedDepartmentModel;
    }

    public Department read(Long id) throws DepartmentNotFoundException {
        LOGGER.info("read(" + id + ")");
        Optional<DepartmentEntity> optionalDepartmentEntity = departmentRepository.findById(id);
        DepartmentEntity departmentEntity = optionalDepartmentEntity.orElseThrow(
                () -> new DepartmentNotFoundException("Brak działu o podanym id " + id));
        Department departmentModel = departmentMapper.entityToModel(departmentEntity);
        LOGGER.info("read(...)" + departmentModel);
        return departmentModel;
    }

    public Department update(Department department) {
        LOGGER.info("update()" + department);
        DepartmentEntity departmentEntity = departmentMapper.modelToEntity(department);
        DepartmentEntity updatedDepartmentEntity = departmentRepository.save(departmentEntity);
        Department updatedDepartmentModel = departmentMapper.entityToModel(updatedDepartmentEntity);
        LOGGER.info("update(...) " + updatedDepartmentModel);
        return updatedDepartmentModel;
    }

    public String delete(Long id) {
        LOGGER.info("delete()");
        departmentRepository.deleteById(id);
        LOGGER.info("delete(...)");
        return "Response: 200 Record " + id + " deleted!";
    }
}
