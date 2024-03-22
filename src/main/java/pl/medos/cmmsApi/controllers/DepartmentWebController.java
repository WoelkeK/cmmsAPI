package pl.medos.cmmsApi.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.exception.DepartmentNotFoundException;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.service.DepartmentService;
import pl.medos.cmmsApi.util.imports.ImportDepartment;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/departments")
@Slf4j
public class DepartmentWebController {
    private DepartmentService departmentService;
    private ImportDepartment importDepartment;

    public DepartmentWebController(DepartmentService departmentService, ImportDepartment importDepartment) {
        this.departmentService = departmentService;
        this.importDepartment = importDepartment;
    }

    @GetMapping(value = "/list")
    public String listViewAll(ModelMap modelMap) {
        log.debug("listView()");
        List<Department> departments = departmentService.findAllDepartments();
        modelMap.addAttribute("departments", departments);
        log.debug("listView(...)" + departments);
        return "list-department.html";
    }

    @GetMapping
    public String listView(Model model) throws IOException {
        log.debug("listView()");
        return findPageinated(1,"name", "desc", model);
    }

    @GetMapping(value = "/page/{pageNo}")
    public String findPageinated(@PathVariable(name = "pageNo") int pageNo,
                                 @RequestParam(name = "sortField") String sortField,
                                 @RequestParam(name = "sortDir") String sortDir,
                                 Model model)  {
        log.debug("findPage()");
        int pageSize=10;
        Page<Department> departmentPage = departmentService.findDepartmentPage(pageNo, pageSize, sortField, sortDir);
        List<Department> departments = departmentPage.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", departmentPage.getTotalPages());
        model.addAttribute("totalItems", departmentPage.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("departments", departments);
        log.debug("listView(...)" + departments);
        return "main-department.html";
    }

    @GetMapping(value = "/create")
    public String createView(ModelMap modelMap) {
        log.debug("createView()");
        modelMap.addAttribute("department", new Department());
        List<Department> departments = departmentService.findAllDepartments();
        modelMap.addAttribute("departments", departments);
        return "create-department";
    }

    @PostMapping(value = "/create")
    public String create(
            @ModelAttribute(name = "department") Department department) {
        log.debug("create(" + department + ")");
        Department savedDepartment = departmentService.createDepartment(department);
        log.debug("create(...)" + savedDepartment);
        return "redirect:/departments";
    }

    @GetMapping(value = "/read/{id}")
    public String read(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws Exception {
        log.debug("read(" + id + ")");
        Department department = departmentService.findDepartmentById(id);
        modelMap.addAttribute("department", department);
        return "read-department.html";
    }

    @GetMapping(value = "/update/{id}")
    public String updateView(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws Exception {
        log.debug("updateView()");
        Department department = departmentService.findDepartmentById(id);
        modelMap.addAttribute("department", department);
        return "update-department";
    }

    @PostMapping(value = "/update/{id}")
    public String update(@PathVariable(name = "id") Long id,
                         @ModelAttribute(name = "department") Department department) throws DepartmentNotFoundException {
        log.debug("update()" + department);
        Department updatedDepartment = departmentService.updateDepartment(department, id);
        log.debug("update(...)" + updatedDepartment);
        return "redirect:/departments";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(
            @PathVariable(name = "id") Long id) {
        log.debug("delete()");
        departmentService.deleteDepartment(id);
        return "redirect:/departments";
    }

    @GetMapping("/file")
    public String showUploadForm() {
        return "uploadDep-form";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {

        log.debug("importDepartments()");
        if(file.isEmpty()){
            log.debug("Please select file to upload");
            return "redirect:/departments";
        }
        List<Department> departments = importDepartment.importExcelDepartmentsData(file);
        departments.forEach((department) -> {
            departmentService.createDepartment(department);
        });
        log.debug("importDepartments(...) ");
        return "redirect:/departments";
    }
}
