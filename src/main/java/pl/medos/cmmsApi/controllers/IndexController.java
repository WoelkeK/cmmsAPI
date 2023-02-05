package pl.medos.cmmsApi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.service.EmployeeService;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/employees")
public class IndexController {

    private static final Logger LOGGER = Logger.getLogger(IndexController.class.getName());
    private EmployeeService employeeService;

    @Autowired
    public IndexController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

//    @GetMapping()
//    public String getHomepage(Model model) {
//        model.addAttribute("employees", employeeService.list());
//        return "index";
//    }

    @GetMapping
    public String listView(ModelMap modelMap) {
        LOGGER.info("listView()");
        List<Employee> employees = employeeService.list();
        modelMap.addAttribute("employees", employees);

        return "list-employeesTW.html";
    }


    @GetMapping(value = "/update/{id}")
    public String updateView(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws Exception {
        LOGGER.info("updateView()");
        Employee employee = employeeService.read(id);
        modelMap.addAttribute("employee", employee);
        return "update-employeeTW.html";
    }

    @PostMapping(value = "/update")
    public String update(
            @ModelAttribute(name = "employee") Employee employee) {
        LOGGER.info("update()");
        employeeService.update(employee);
        return "redirect:/employees";

    }


    @GetMapping(value = "/create")
    public String createView(ModelMap modelMap) {
        LOGGER.info("createView()");
        modelMap.addAttribute("employee", new Employee());
        return "create-employeeForm.html";
    }

    @PostMapping(value = "/create")
//    public String create(String firstName, String lastName) {
    public String create(
            @ModelAttribute(name = "employee") Employee employee) {
        LOGGER.info("create(" + employee + ")");
//        LOGGER.info("create(" + lastName + ")");
//        employee.setPassword(passwordEncoder.encode(clientModel.getPassword()));
        employeeService.create(employee);
        return "redirect:/employees";
    }

    @GetMapping(value = "/read/{id}")
    public String read(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws Exception {
        LOGGER.info("read(" + id + ")");
        Employee employee = employeeService.read(id);
        modelMap.addAttribute("employee", employee);
        return "read-employee.html";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(
            @PathVariable(name = "id") Long id) {
        LOGGER.info("delete()");
        employeeService.delete(id);
        return "redirect:/employees";
    }

}
