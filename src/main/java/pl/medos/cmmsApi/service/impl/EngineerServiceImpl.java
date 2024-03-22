package pl.medos.cmmsApi.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.exception.EmployeeNotFoundException;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.model.Engineer;
import pl.medos.cmmsApi.repository.EngineerRepository;
import pl.medos.cmmsApi.repository.entity.EmployeeEntity;
import pl.medos.cmmsApi.repository.entity.EngineerEntity;
import pl.medos.cmmsApi.service.EngineerService;
import pl.medos.cmmsApi.service.mapper.EngineerMapper;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@Slf4j
public class EngineerServiceImpl implements EngineerService {

    private EngineerRepository engineerRepository;
    private EngineerMapper engineerMapper;

    public EngineerServiceImpl(EngineerRepository engineerRepository, EngineerMapper engineerMapper) {
        this.engineerRepository = engineerRepository;
        this.engineerMapper = engineerMapper;
    }

    @Override
    public List<Engineer> finadAllEngineers() {
        log.debug("findAllEngineers()");
        List<EngineerEntity> engineerEntities = engineerRepository.findAll();
        List<Engineer> engineers = engineerMapper.listModels(engineerEntities);
        log.debug("findAllEngineer(...)");
        return engineers;
    }

    @Override
    public Engineer createEngineer(Engineer engineer) {
        log.debug("createEngineer()");
        EngineerEntity engineerEntity = engineerMapper.modelToEntity(engineer);
        EngineerEntity savedEngineerEntity = engineerRepository.save(engineerEntity);
        Engineer savedEngineerModel = engineerMapper.entityToModel(savedEngineerEntity);
        log.debug("createEngineer(...) " + savedEngineerModel);
        return savedEngineerModel;
    }

    public Engineer findEngineerById(Long id) throws EmployeeNotFoundException {

        log.debug("read( " + id + " )");
        Optional<EngineerEntity> optionalEngineerEntity = engineerRepository.findById(id);
        EngineerEntity engineerEntity = optionalEngineerEntity.orElseThrow(
                () -> new EmployeeNotFoundException("Brak pracownika o podanym id " + id));
        Engineer engineerModel = engineerMapper.entityToModel(engineerEntity);
        log.debug("read(...)" + engineerModel);
        return engineerModel;
    }

    @Override
    public Page<Engineer> findPageinatedQuery(int pageNo, int pageSize, String sortField, String sortDir, String query) {
        log.debug("findPaginated()");
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        Page<EngineerEntity> engineerEntityPage = engineerRepository.findByQueryPagable(query, pageable);
        Page<Engineer> engineers = engineerMapper.mapPageEntitiestoModels(engineerEntityPage);
        log.debug("findPaginated(...)");
        return engineers;
    }

    @Override
    public Page<Engineer> findPageinated(int pageNo, int pageSize, String sortField, String sortDir) {
        log.debug("findPageinated()");
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        Page<EngineerEntity> engineerEntityPage = engineerRepository.findAll(pageable);
        Page<Engineer> engineerPage = engineerMapper.mapPageEntitiestoModels(engineerEntityPage);
        log.debug("findPageinated(...)");
        return engineerPage;
    }

    @Override
    public Engineer findByName(String name) {
        log.debug("findByName" + name);
        List<EngineerEntity> engineerEntities = engineerRepository.searchEngineerByName(name);
        EngineerEntity engineerEntity = engineerEntities.stream().findAny().orElse(new EngineerEntity());
        Engineer engineer = engineerMapper.entityToModel(engineerEntity);
        log.debug("findByName(...)");
        return engineer;
    }

    @Override
    public Page<Engineer> findByActive(int pageNo, int pageSize, String sortField, String sortDir, Boolean profile) {
        log.debug("findPagesEnginnerByProfileStatus()");
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        Page<EngineerEntity> engineerEntityPage = engineerRepository.findByIsActive(pageable, profile);
        Page<Engineer> engineers = engineerMapper.mapPageEntitiestoModels(engineerEntityPage);
        log.debug("findPaginated(...)");
        return engineers;
    }

    @Override
    public Engineer updateEngineer(Engineer engineer, Long id) throws EmployeeNotFoundException {
        log.debug("update( " + engineer.getId() + " )");
        Optional<EngineerEntity> optionalEngineerEntity = engineerRepository.findById(id);
        EngineerEntity engineerEntity = optionalEngineerEntity.orElseThrow(
                () -> new EmployeeNotFoundException("Brak pracownika o podanym id " + id));
        EngineerEntity editedEngineerEntity = engineerMapper.modelToEntity(engineer);
        editedEngineerEntity.setId(engineerEntity.getId());
        EngineerEntity updatedEngineerEntity = engineerRepository.save(editedEngineerEntity);
        Engineer updatedEngineerModel = engineerMapper.entityToModel(updatedEngineerEntity);
        log.debug("update(...)" + updatedEngineerModel.getId());
        return updatedEngineerModel;
    }

    @Override
    public void deleteEngineer(Long id) {
        log.debug("delete(" + id + ")");
        engineerRepository.deleteById(id);
        log.debug("delete(...)");
    }

    @Override
    public Page<Engineer> findEngineerByName(int pageNo, int pagesize, String query) {
        log.debug("findEmployeeByName()" + query);
        Pageable pageable = PageRequest.of(pageNo - 1, pagesize);
        Page<EngineerEntity> engineerEntities = engineerRepository.searchEngineerByQuery(pageable, query);
        Page<Engineer> engineers = engineerMapper.mapPageEntitiestoModels(engineerEntities);
        log.debug("findEmployeeByName(...)");
        return engineers;
    }

    @Override
    public List<Engineer> findEngineerByName(String engineerName) {
        log.debug("findEngineerByName()" + engineerName);
        List<EngineerEntity> engineerEntities = engineerRepository.searchEngineerByName(engineerName);
        List<Engineer> engineers = engineerMapper.listModels(engineerEntities);
        log.debug("findEngineerByName(...)");
        return engineers;
    }

    @Override
    public void deleteAll() {
        log.debug("deleteAll");
        engineerRepository.deleteAll();
    }
}
