package pl.medos.cmmsApi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.CostNotFoundException;
import pl.medos.cmmsApi.model.Cost;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.service.CostService;
import pl.medos.cmmsApi.service.DepartmentService;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/costs")
public class WebCostController {

    private static final Logger LOGGER = Logger.getLogger(WebCostController.class.getName());
    private CostService costServicee;

    public WebCostController(CostService costServicee) {
        this.costServicee = costServicee;
    }

    @GetMapping
    public String listView(ModelMap modelMap) {
        LOGGER.info("listView()");
        List<Cost> costs = costServicee.findAllCosts();
        modelMap.addAttribute("costs", costs);
        LOGGER.info("listView(...)" + costs);
        return "list-cost.html";
    }

    @GetMapping(value = "/update/{id}")
    public String updateView(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws Exception {
        LOGGER.info("updateView()");
        Cost costById = costServicee.findCostById(id);
        modelMap.addAttribute("cost", costById);
        return "update-cost.html";
    }

    @PostMapping(value = "/update/{id}")
    public String update(@PathVariable (name="id")Long id,
            @ModelAttribute(name = "cost") Cost cost) throws CostNotFoundException {
        LOGGER.info("update()" + cost);
        Cost updatedCost = costServicee.updateCost(cost, id);
        LOGGER.info("update(...)" + updatedCost);
        return "redirect:/costs";
    }

    @GetMapping(value = "/create")
    public String createView(ModelMap modelMap) {
        LOGGER.info("createView()");
        modelMap.addAttribute("cost", new Cost());
        LOGGER.info("createView(...)");
        return "create-cost.html";
    }

    @PostMapping(value = "/create")
    public String create(
            @ModelAttribute(name = "cost") Cost cost) {
        LOGGER.info("create(" + cost + ")");
        Cost savedCost = costServicee.createCost(cost);
        LOGGER.info("create(...)" + savedCost);
        return "redirect:/costs";
    }

    @GetMapping(value = "/read/{id}")
    public String read(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws Exception {
        LOGGER.info("read(" + id + ")");
        Cost costById = costServicee.findCostById(id);
        modelMap.addAttribute("cost", costById);
        return "read-cost.html";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(
            @PathVariable(name = "id") Long id) {
        LOGGER.info("delete()");
        costServicee.deleteCostById(id);
        return "redirect:/costs";
    }
}
