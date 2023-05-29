package pl.medos.cmmsApi.controllers;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.service.DepartmentService;
import pl.medos.cmmsApi.service.EmployeeService;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/info")
@SessionAttributes(names = {"info", "departments"})
public class WebInfoPortal {

    private static final Logger LOGGER = Logger.getLogger(WebInfoPortal.class.getName());

    private EmployeeService employeeService;
    private DepartmentService departmentService;

    public WebInfoPortal(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @GetMapping(value = "/list")
    public String listViewAll(ModelMap modelMap) throws IOException {
        LOGGER.info("listView()");
        List<Employee> employees = employeeService.finadAllEmployees();
        modelMap.addAttribute("employees", employees);
        List<Department> departments = departmentService.findAllDepartments();
        modelMap.addAttribute("departments", departments);
        return "list-info.html";
    }

    @GetMapping
    public String listView(Model model) throws IOException {
        LOGGER.info("listView()");
        return findPaginated(1, model);
    }

    @GetMapping(value = "/page/{pageNo}")
    public String findPaginated(
            @PathVariable(value = "pageNo") int pageNo,
            Model model) throws IOException {
        int pageSize = 10;
        LOGGER.info("findPage()");
        Page<Employee> pageEmployees = employeeService.findPageinated(pageNo, pageSize);
        List<Employee> employees = pageEmployees.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", pageEmployees.getTotalPages());
        model.addAttribute("totalItems", pageEmployees.getTotalElements());
        model.addAttribute("employees", employees);
        List<Department> departments = departmentService.findAllDepartments();
        model.addAttribute("departments", departments);
        return "list-info.html";
    }

    @GetMapping("/search/name")
    public String searchEmployeeByName(@RequestParam(value = "employeeName") String query,
                                       Model model) {
        LOGGER.info("search()");
        List<Employee> employeeByName = employeeService.findEmployeeByName(query);
        LOGGER.info("findEmployeeByName()" + employeeByName);
        model.addAttribute("employees", employeeByName);
        return "list-info";
    }
}