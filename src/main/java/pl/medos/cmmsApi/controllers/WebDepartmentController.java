package pl.medos.cmmsApi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.dto.EmployeesImportDto;
import pl.medos.cmmsApi.exception.DepartmentNotFoundException;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.service.DepartmentService;
import pl.medos.cmmsApi.service.ImportService;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/departments")
public class WebDepartmentController {

    private static final Logger LOGGER = Logger.getLogger(WebDepartmentController.class.getName());
    private DepartmentService departmentService;
    private ImportService importService;

    public WebDepartmentController(DepartmentService departmentService, ImportService importService) {
        this.departmentService = departmentService;
        this.importService = importService;
    }

    @GetMapping
    public String listView(ModelMap modelMap) {
        LOGGER.info("listView()");
        List<Department> departments = departmentService.findAllDepartments();
        modelMap.addAttribute("departments", departments);
        LOGGER.info("listView(...)" + departments);
        return "list-department.html";
    }

    @GetMapping(value = "/create")
    public String createView(ModelMap modelMap) {
        LOGGER.info("createView()");
        modelMap.addAttribute("department", new Department());
        List<Department> departments = departmentService.findAllDepartments();
        modelMap.addAttribute("departments", departments);
        return "create-department.html";
    }

    @PostMapping(value = "/create")
    public String create(
            @ModelAttribute(name = "department") Department department) {
        LOGGER.info("create(" + department + ")");
        Department savedDepartment = departmentService.createDepartment(department);
        LOGGER.info("create(...)" + savedDepartment);
        return "redirect:/departments";
    }

    @GetMapping(value = "/read/{id}")
    public String read(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws Exception {
        LOGGER.info("read(" + id + ")");
        Department department = departmentService.findDepartmentById(id);
        modelMap.addAttribute("department", department);
        return "read-department.html";
    }

    @GetMapping(value = "/update/{id}")
    public String updateView(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws Exception {
        LOGGER.info("updateView()");
        Department department = departmentService.findDepartmentById(id);
        modelMap.addAttribute("department", department);
        return "update-department.html";
    }

    @PostMapping(value = "/update/{id}")
    public String update(@PathVariable(name = "id") Long id,
                         @ModelAttribute(name = "department") Department department) throws DepartmentNotFoundException {
        LOGGER.info("update()" + department);
        Department updatedDepartment = departmentService.updateDepartment(department, id);
        LOGGER.info("update(...)" + updatedDepartment);
        return "redirect:/departments";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(
            @PathVariable(name = "id") Long id) {
        LOGGER.info("delete()");
        departmentService.deleteDepartment(id);
        return "redirect:/departments";
    }


    @GetMapping("/file")
    public String showUploadForm() {
        return "uploadDep-form";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {

        LOGGER.info("importDepartments()");
        if(file.isEmpty()){
            LOGGER.info("Please select file to upload");
            return "redirect/departments";
        }

        EmployeesImportDto employeesImportDto = new EmployeesImportDto();
        List<Department> departments = importService.importExcelDepartmentsData(file);

        departments.forEach((department) -> {
            departmentService.createDepartment(department);
        });
        LOGGER.info("importDepartments(...) ");

        return "redirect:/departments";
    }
}
