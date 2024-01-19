package pl.medos.cmmsApi.service.impl;

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
public class EngineerServiceImpl implements EngineerService {

    private static final Logger LOGGER = Logger.getLogger(EngineerServiceImpl.class.getName());

    private EngineerRepository engineerRepository;
    private EngineerMapper engineerMapper;

    public EngineerServiceImpl(EngineerRepository engineerRepository, EngineerMapper engineerMapper) {
        this.engineerRepository = engineerRepository;
        this.engineerMapper = engineerMapper;
    }

    @Override
    public List<Engineer> finadAllEngineers() {
        LOGGER.info("findAllEngineers()");
        List<EngineerEntity> engineerEntities = engineerRepository.findAll();
        List<Engineer> engineers = engineerMapper.listModels(engineerEntities);
        LOGGER.info("findAllEngineer(...)");
        return engineers;
    }

    @Override
    public Engineer createEngineer(Engineer engineer) {
        LOGGER.info("createEngineer()");
        EngineerEntity engineerEntity = engineerMapper.modelToEntity(engineer);
        EngineerEntity savedEngineerEntity = engineerRepository.save(engineerEntity);
        Engineer savedEngineerModel = engineerMapper.entityToModel(savedEngineerEntity);
        LOGGER.info("createEngineer(...) " + savedEngineerModel);
        return savedEngineerModel;
    }

    public Engineer findEngineerById(Long id) throws EmployeeNotFoundException {

        LOGGER.info("read( " + id + " )");
        Optional<EngineerEntity> optionalEngineerEntity = engineerRepository.findById(id);
        EngineerEntity engineerEntity = optionalEngineerEntity.orElseThrow(
                () -> new EmployeeNotFoundException("Brak pracownika o podanym id " + id));
        Engineer engineerModel = engineerMapper.entityToModel(engineerEntity);
        LOGGER.info("read(...)" + engineerModel);
        return engineerModel;
    }

    @Override
    public Page<Engineer> findPageinatedQuery(int pageNo, int pageSize, String sortField, String sortDir, String query) {
        LOGGER.info("findPaginated()");
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        Page<EngineerEntity> engineerEntityPage = engineerRepository.findByQueryPagable(query, pageable);
        Page<Engineer> engineers = engineerMapper.mapPageEntitiestoModels(engineerEntityPage);
        LOGGER.info("findPaginated(...)");
        return engineers;
    }

    @Override
    public Page<Engineer> findPageinated(int pageNo, int pageSize, String sortField, String sortDir) {
        LOGGER.info("findPageinated()");
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize,sort);
        Page<EngineerEntity> engineerEntityPage = engineerRepository.findAll(pageable);
        Page<Engineer> engineerPage = engineerMapper.mapPageEntitiestoModels(engineerEntityPage);
        LOGGER.info("findPageinated(...)");
        return engineerPage;
    }

    @Override
    public Engineer updateEngineer(Engineer engineer, Long id) throws EmployeeNotFoundException {
        LOGGER.info("update( " + engineer.getId() + " )");
        Optional<EngineerEntity> optionalEngineerEntity = engineerRepository.findById(id);
        EngineerEntity engineerEntity = optionalEngineerEntity.orElseThrow(
                () -> new EmployeeNotFoundException("Brak pracownika o podanym id " + id));

        EngineerEntity editedEngineerEntity = engineerMapper.modelToEntity(engineer);
        editedEngineerEntity.setId(engineerEntity.getId());
        EngineerEntity updatedEngineerEntity = engineerRepository.save(editedEngineerEntity);
        Engineer updatedEngineerModel = engineerMapper.entityToModel(updatedEngineerEntity);
        LOGGER.info("update(...)" + updatedEngineerModel.getId());
        return updatedEngineerModel;
    }

    @Override
    public void deleteEngineer(Long id) {
        LOGGER.info("delete(" + id + ")");
       engineerRepository.deleteById(id);
        LOGGER.info("delete(...)");
    }

    @Override
    public Page<Engineer> findEngineerByName(int pageNo, int pagesize, String query) {
        return null;
    }

    @Override
    public List<Engineer> findEngineerByName(String engineerName) {
        LOGGER.info("findEngineerByName()" + engineerName);
        List<EngineerEntity> engineerEntities = engineerRepository.searchEngineerByName(engineerName);
        List<Engineer> engineers = engineerMapper.listModels(engineerEntities);
        LOGGER.info("findEngineerByName(...)");
        return engineers;
    }

    @Override
    public void deleteAll() {
        LOGGER.info("deleteAll");
        engineerRepository.deleteAll();
            }
}
