package pl.medos.cmmsApi.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.CostNotFoundException;
import pl.medos.cmmsApi.model.Cost;
import pl.medos.cmmsApi.service.CostService;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CostController {

    private static final Logger LOGGER = Logger.getLogger(CostController.class.getName());

    private final CostService costService;

    @GetMapping("/costs")
    private List<Cost> findAllCosts() {
        LOGGER.info("findAllCosts()");
        List<Cost> costs = costService.findAllCosts();
        LOGGER.info("findAllCosts(...)");
        return costs;
    }

    @PostMapping("/cost")
    private Cost createCost(@RequestBody Cost cost) {
        LOGGER.info("createCost()");
        Cost savedCost = costService.createCost(cost);
        LOGGER.info("createCost(...)" + savedCost);
        return savedCost;
    }

    @GetMapping("/cost/{id}")
    private Cost findCostById(@PathVariable(name = "id") Long id) throws CostNotFoundException {
        LOGGER.info("findCostById()");
        Cost costById = costService.findCostById(id);
        LOGGER.info("findCostById(...)" + costById);
        return costById;
    }

    @PutMapping("/cost/{id}")
    private Cost updateCost(@PathVariable(name = "id") Long id,
                            @RequestBody Cost cost) throws CostNotFoundException {
        LOGGER.info("updateCost()");
        Cost updatedcost = costService.updateCost(cost, id);
        LOGGER.info("updateCost(...)");
        return updatedcost;
    }

    @DeleteMapping("/cost/{id}")
    private void deletecost(@PathVariable(name = "id") Long id) {
        LOGGER.info("deleteCost()");
        costService.deleteCostById(id);
        LOGGER.info("deleteCost(...)");

    }
}
