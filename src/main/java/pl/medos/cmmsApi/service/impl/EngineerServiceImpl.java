package pl.medos.cmmsApi.service.impl;

import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.exception.EmployeeNotFoundException;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.model.Engineer;
import pl.medos.cmmsApi.repository.EmployeeRepository;
import pl.medos.cmmsApi.repository.EngineerRepository;
import pl.medos.cmmsApi.repository.entity.EmployeeEntity;
import pl.medos.cmmsApi.repository.entity.EngineerEntity;
import pl.medos.cmmsApi.service.EmployeeService;
import pl.medos.cmmsApi.service.EngineerService;
import pl.medos.cmmsApi.service.mapper.EmployeeMapper;
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
    public List<Engineer> finadAllEmployees() {
        LOGGER.info("findAllEngineer()");
        List<EngineerEntity> engineerEntities = engineerRepository.findAll();
        List<Engineer> engineers = engineerMapper.listModels(engineerEntities);
        LOGGER.info("findAllEngineer(...)");
        return engineers;
    }

    @Override
    public Engineer createEmployee(Engineer engineer) {
        LOGGER.info("createEngineer()");
        EngineerEntity engineerEntity = engineerMapper.modelToEntity(engineer);
        EngineerEntity savedEngineerEntity = engineerRepository.save(engineerEntity);
        Engineer savedEngineerModel = engineerMapper.entityToModel(savedEngineerEntity);
        LOGGER.info("createEngineer(...) " + savedEngineerModel);
        return savedEngineerModel;
    }

    public Engineer findEmployeeById(Long id) throws EmployeeNotFoundException {

        LOGGER.info("read( " + id + " )");
        Optional<EngineerEntity> optionalEngineerEntity = engineerRepository.findById(id);
        EngineerEntity engineerEntity = optionalEngineerEntity.orElseThrow(
                () -> new EmployeeNotFoundException("Brak pracownika o podanym id " + id));
        Engineer engineerModel = engineerMapper.entityToModel(engineerEntity);
        LOGGER.info("read(...)" + engineerModel);
        return engineerModel;
    }

    @Override
    public Engineer updateEmployee(Engineer engineer, Long id) throws EmployeeNotFoundException {
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
    public void deleteEmployee(Long id) {
        LOGGER.info("delete(" + id + ")");
       engineerRepository.deleteById(id);
        LOGGER.info("delete(...)");
    }

    @Override
    public List<Engineer> findEmployeeByName(String employeeName) {
        LOGGER.info("findEmployeeByName()" + employeeName);
        List<EngineerEntity> engineerEntities = engineerRepository.searchEmployeeByName(employeeName);
        List<Engineer> engineers = engineerMapper.listModels(engineerEntities);
        LOGGER.info("findEmployeeByName(...)");
        return engineers;
    }
}
