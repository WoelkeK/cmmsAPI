package pl.medos.cmmsApi.service;

import pl.medos.cmmsApi.exception.CostNotFoundException;
import pl.medos.cmmsApi.model.Cost;

import java.util.List;

public interface CostService {

    List<Cost> findAllCosts();

    Cost createCost(Cost cost);

    Cost findCostById(Long id) throws CostNotFoundException;

    Cost updateCost(Cost cost, Long id) throws CostNotFoundException;

    void deleteCostById(Long id);

    Cost searchCostByUnit(String unit);

}
