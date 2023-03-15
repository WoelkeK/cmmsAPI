package pl.medos.cmmsApi.service.impl;

import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.exception.CostNotFoundException;
import pl.medos.cmmsApi.model.Cost;
import pl.medos.cmmsApi.repository.CostRepository;
import pl.medos.cmmsApi.repository.entity.CostEntity;
import pl.medos.cmmsApi.service.CostService;
import pl.medos.cmmsApi.service.mapper.CostMapper;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class CostServiceImpl implements CostService {

    private static final Logger LOGGER = Logger.getLogger(CostServiceImpl.class.getName());

    private CostMapper costMapper;
    private CostRepository costRepository;

    public CostServiceImpl(CostMapper costMapper, CostRepository costRepository) {
        this.costMapper = costMapper;
        this.costRepository = costRepository;
    }

    @Override
    public List<Cost> findAllCosts() {
        LOGGER.info("findAllCosts()");
        List<Cost> costs = costMapper.listModels();
        LOGGER.info("findAllCosts(...)");
        return costs;
    }

    @Override
    public Cost createCost(Cost cost) {
        LOGGER.info("createCost()" + cost);
        CostEntity costEntity = costMapper.modelToEntity(cost);
        CostEntity savedCostEntity = costRepository.save(costEntity);
        Cost savedCost = costMapper.entityToModel(savedCostEntity);
        LOGGER.info("createCost(...)" + savedCost);
        return savedCost;
    }

    @Override
    public Cost findCostById(Long id) throws CostNotFoundException {
        LOGGER.info("findCostById()");
        Optional<CostEntity> optionalCostEntity = costRepository.findById(id);
        CostEntity costEntity = optionalCostEntity.orElseThrow(
                () -> new CostNotFoundException("Nie znaleziono kosztu o podanym id ")
        );
        Cost cost = costMapper.entityToModel(costEntity);
        LOGGER.info("findCostById(...)");
        return cost;
    }

    @Override
    public Cost updateCost(Cost cost) {
        LOGGER.info("updateCost()");
        CostEntity editedCostEntity = costMapper.modelToEntity(cost);
        CostEntity updatedCostEntity = costRepository.save(editedCostEntity);
        Cost updateCost = costMapper.entityToModel(updatedCostEntity);
        LOGGER.info("updateCost(...)" + updateCost);
        return updateCost;
    }

    @Override
    public void deleteCostById(Long id) {
        LOGGER.info("deleteCostById()");
        costRepository.deleteById(id);
        LOGGER.info("deleteCostById(...)");
    }

    @Override
    public Cost searchCostByUnit(String unit) {
        LOGGER.info("searchCostById()");
        CostEntity costEntityByUnit = costRepository.searchCostByUnit(unit);
        Cost cost = costMapper.entityToModel(costEntityByUnit);
        LOGGER.info("searchCostById(...)" + cost);
        return cost;
    }
}
