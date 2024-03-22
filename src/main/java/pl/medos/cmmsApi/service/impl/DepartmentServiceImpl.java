package pl.medos.cmmsApi.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.exception.DepartmentNotFoundException;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.repository.DepartmentRepository;
import pl.medos.cmmsApi.repository.JobRepository;
import pl.medos.cmmsApi.repository.entity.DepartmentEntity;
import pl.medos.cmmsApi.service.DepartmentService;
import pl.medos.cmmsApi.service.mapper.DepartmentMapper;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
@Service
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;
    private DepartmentMapper departmentMapper;
    private final JobRepository jobRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper,
                                 JobRepository jobRepository) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
        this.jobRepository = jobRepository;
    }

    @Override
    public List<Department> findAllDepartments() {
        log.debug("findAllDepartments()");
        List<DepartmentEntity> departmentEntities = departmentRepository.findAll();
        List<Department> departmentModels = departmentMapper.listModels(departmentEntities);
        log.debug("findAllDepartments(...)");
        return departmentModels;
    }
    @Override
    public Department findDepartmentByName(String name) {
        log.debug("findDeaprtmentsByName()" + name);
        List<DepartmentEntity> departmentEntity = departmentRepository.searchDepartmentByName(name);
        Department department = departmentMapper.entityToModel(departmentEntity.get(0));
        log.debug("findDepartmentsByName(...)" + department.getName());
        return department;
    }

    @Override
    public Page<Department> findDepartmentPage(int pageNo, int size, String sortField, String sortDirection) {
        log.debug("findDepartmentPage(" + pageNo + ")");
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, size, sort);
        Page<DepartmentEntity> departmentEntities = departmentRepository.findAll(pageable);
        Page<Department> departmentPage = departmentMapper.mapEntitiesToModelsPage(departmentEntities);
        log.debug("findDepartmentPage(...)");
        return departmentPage;
    }

    @Override
    public Department findByName(String name) {
        log.debug("findDeaprtmentsByName()" + name);
        DepartmentEntity departmentEntity = departmentRepository.findByName(name);
        log.debug("Repository found " + departmentEntity.getName());
        Department department = departmentMapper.entityToModel(departmentEntity);
        log.debug("findDepartmentsByName(...)" + department.getName());
        return department;
    }

    @Override
    public void deleteAll() {
        log.debug("deleteAll()");
        departmentRepository.deleteAll();
        log.debug("deleteAll(...)");
    }

    @Override
    public Department createDepartment(Department department) {
        log.debug("create(" + department + ")");
        DepartmentEntity departmentEntity = departmentMapper.modelToEntity(department);
        DepartmentEntity savedDepartmentEntity = departmentRepository.save(departmentEntity);
        Department savedDepartmentModel = departmentMapper.entityToModel(savedDepartmentEntity);
        log.debug("create(...)" + savedDepartmentModel);
        return savedDepartmentModel;
    }

    @Override
    public Department findDepartmentById(Long id) throws DepartmentNotFoundException {
        log.debug("read(" + id + ")");
        Optional<DepartmentEntity> optionalDepartmentEntity = departmentRepository.findById(id);
        DepartmentEntity departmentEntity = optionalDepartmentEntity.orElseThrow(
                () -> new DepartmentNotFoundException("Brak działu o podanym id " + id));
        Department departmentModel = departmentMapper.entityToModel(departmentEntity);
        log.debug("read(...)" + departmentModel);
        return departmentModel;
    }

    @Override
    public Department updateDepartment(Department department, Long id) throws DepartmentNotFoundException {
        log.debug("update()" + department);
        Optional<DepartmentEntity> optionalDepartmentEntity = departmentRepository.findById(id);
        DepartmentEntity departmentEntity = optionalDepartmentEntity.orElseThrow(
                () -> new DepartmentNotFoundException("Brak działu o podanym id " + id));
        DepartmentEntity editedDepartmentEntity = departmentMapper.modelToEntity(department);
        editedDepartmentEntity.setId(id);
        DepartmentEntity updatedDepartmentEntity = departmentRepository.save(editedDepartmentEntity);
        Department updatedDepartmentModel = departmentMapper.entityToModel(updatedDepartmentEntity);
        log.debug("update(...) " + updatedDepartmentModel);
        return updatedDepartmentModel;
    }

    @Override
    public void deleteDepartment(Long id) {
        log.debug("delete()");
        departmentRepository.deleteById(id);
        log.debug("delete(...)");

    }
}
