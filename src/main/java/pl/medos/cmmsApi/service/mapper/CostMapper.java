package pl.medos.cmmsApi.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.medos.cmmsApi.model.Cost;
import pl.medos.cmmsApi.repository.CostRepository;
import pl.medos.cmmsApi.repository.entity.CostEntity;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class CostMapper {

    private static final Logger LOGGER = Logger.getLogger(CostMapper.class.getName());

    private CostRepository costRepository;

    public CostMapper(CostRepository costRepository) {
        this.costRepository = costRepository;

    }

    public List<Cost> listModels() {
        LOGGER.info("listModels()");
        List<CostEntity> costEntities = costRepository.findAll();
        List<Cost> costs = costEntities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());
        LOGGER.info("listModels(...)");
        return costs;
    }

    public Cost entityToModel(CostEntity costEntity) {
        LOGGER.info("entityToModel()");
        ModelMapper modelMapper = new ModelMapper();
        Cost cost = modelMapper.map(costEntity, Cost.class);
        LOGGER.info("entityToModel(...)" + cost);
        return cost;
    }

    public CostEntity modelToEntity(Cost cost) {
        LOGGER.info("modelToEntity()");
        ModelMapper modelMapper = new ModelMapper();
        CostEntity costEntity = modelMapper.map(cost, CostEntity.class);
        LOGGER.info("modelToEntity(...)" + costEntity);
        return costEntity;
    }
}
