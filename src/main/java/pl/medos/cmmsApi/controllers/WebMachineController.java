package pl.medos.cmmsApi.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.exception.DepartmentNotFoundException;
import pl.medos.cmmsApi.exception.MachineNotFoundException;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Machine;
import pl.medos.cmmsApi.service.DepartmentService;
import pl.medos.cmmsApi.service.ExportService;
import pl.medos.cmmsApi.service.MachineService;
import pl.medos.cmmsApi.util.imports.ImportMachine;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/machines")
@SessionAttributes(names = {"departments", "machines"})
@Slf4j
public class WebMachineController {

    private MachineService machineService;
    private DepartmentService departmentService;
    private ExportService exportService;
    private ImportMachine importMachine;

    public WebMachineController(MachineService machineService, DepartmentService departmentService, ExportService exportService, ImportMachine importMachine) {
        this.machineService = machineService;
        this.departmentService = departmentService;
        this.exportService = exportService;
        this.importMachine = importMachine;
    }

    @GetMapping(value = "/list")
    public String listViewAll(Model model) {
        log.debug("listViewAll()");
        List<Department> departments = departmentService.findAllDepartments();
        model.addAttribute("departments", departments);
        List<Machine> machines = machineService.findAllMachines();
        model.addAttribute("machines", machines);
        log.debug("listView(...)" + machines);
        return "list-machine";
    }

    @GetMapping
    public String listView(@RequestParam(name = "pageNo", defaultValue = "1") int page, Model model) throws IOException {
        log.debug("listView()");
        return findPageinated(page, "name", "desc", model);
    }

    @GetMapping(value = "/page/{pageNo}")
    public String findPageinated(@PathVariable(name = "pageNo") int pageNo,
                                 @RequestParam(name = "sortField") String sortField,
                                 @RequestParam(name = "sortDir") String sortDir,
                                 Model model) throws IOException {
        log.debug("findPage()");
        int pageSize = 10;
        List<Department> departments = departmentService.findAllDepartments();
        model.addAttribute("departments", departments);
        Page<Machine> machinePage = machineService.findPageinated(pageNo, pageSize, sortField, sortDir);
        List<Machine> machines = machinePage.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", machinePage.getTotalPages());
        model.addAttribute("totalItems", machinePage.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("machines", machines);
        log.debug("listView(...)" + machines);
        return "main-machine";

    }

    @GetMapping(value = "/search/query")
    public String searchMachineByQuery(@RequestParam(value = "query") String query,
                                       Model model) {
        log.debug("search()");
        int pageSize = 10;
        int pageNo = 1;
        String sortField = "name";
        String sortDir = "desc";
        List<Department> departments = departmentService.findAllDepartments();
        model.addAttribute("departments", departments);
        Page<Machine> machinePage = machineService.findPageinatedQuery(pageNo, pageSize, sortField, sortDir, query);
        List<Machine> machines = machinePage.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", machinePage.getTotalPages());
        model.addAttribute("totalItems", machinePage.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("machines", machines);
        return "main-machine";
    }

    @GetMapping(value = "/search/machine")
    public String searchJobsByMachineId(@RequestParam(value = "machineName") String machineName,
                                        Model model) throws MachineNotFoundException {
        log.debug("search()");
        Machine machines = machineService.findMachineById(Long.parseLong(machineName));
        model.addAttribute("machines", machines);
        return "main-machine";
    }

    @GetMapping(value = "/search/department")
    public String searchJMachineByDepartment(@RequestParam(value = "machineDepartment") String departmentName,
                                             Model model) throws DepartmentNotFoundException {
        log.debug("search()");
        Department departmentByName = departmentService.findDepartmentById(Long.parseLong(departmentName));
        List<Machine> machines = machineService.findMachinesByDepartment(departmentByName);
        model.addAttribute("machines", machines);
        return "list-machine";
    }

    @GetMapping(value = "/update/{id}")
    public String updateView(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "pageNo") int pageNo,
            ModelMap modelMap) throws Exception {
        log.debug("updateView()");
        Machine machine = machineService.findMachineById(id);
        modelMap.addAttribute("machine", machine);
        List<Department> departments = departmentService.findAllDepartments();
        modelMap.addAttribute("departments", departments);
        modelMap.addAttribute("pageNo", pageNo);
        return "update-machine";
    }

    @PostMapping(value = "/update/{id}")
    public String update(@PathVariable(name = "id") Long id,
                         @RequestParam(name = "pageNo") int pageNo,
                         @ModelAttribute(name = "machine") Machine machine) throws MachineNotFoundException {
        log.debug("update()" + machine);
        Machine savedMachine = machineService.updateMachine(machine, id);
        log.debug("update(...)" + savedMachine);
        return "redirect:/machines?pageNo=" + pageNo;
    }

    @GetMapping(value = "/create")
    public String createView(ModelMap modelMap) {
        log.debug("createView()");
        modelMap.addAttribute("machine", new Machine());
        List<Department> departments = departmentService.findAllDepartments();
        modelMap.addAttribute("departments", departments);
        return "create-machine.html";
    }

    @PostMapping(value = "/create")
    public String create(
            String departmentId,
            @ModelAttribute(name = "Machine") Machine machine) {
        log.debug("create(" + departmentId + ")");
        log.debug("create(" + machine + ")");
        Machine savedMachine = machineService.createMachine(machine);
        log.debug("create(...)" + savedMachine);
        return "redirect:/machines";
    }

    @GetMapping(value = "/read/{id}")
    public String read(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws Exception {
        log.debug("read(" + id + ")");
        Machine machine = machineService.findMachineById(id);
        modelMap.addAttribute("machine", machine);
        return "read-machine.html";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(
            @RequestParam(name = "pageNo") int pageNo,
            @PathVariable(name = "id") Long id) {
        log.debug("delete()");
        machineService.deleteMachine(id);
        return "redirect:/machines?pageNo=" + pageNo;
    }

    @GetMapping(value = "/shedule/{id}")
    public String sheduleView(
            @PathVariable(name = "id") Long id,
            ModelMap modelMap) throws Exception {
        log.debug("sheduleView()");
        Machine machine = machineService.findMachineById(id);
        modelMap.addAttribute("machine", machine);
        List<Department> departments = departmentService.findAllDepartments();
        modelMap.addAttribute("departments", departments);
        return "shedule-machine.html";
    }

    @GetMapping(value = "/export")
    public void exportMachines(@ModelAttribute(name = "machines") List<Machine> machines,
                               HttpServletResponse response, Model model) throws Exception {
        log.debug("export()");
        machines = machineService.findAllMachines();
        response.setContentType("application/octet-stream");
        DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateTimeFormat.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=maszyny" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        exportService.excelMachineModelGenerator(machines);
        exportService.generateExcelFile(response);
        response.flushBuffer();
        log.debug("export(...)");
    }

    @GetMapping(value = "/file")
    public String showUploadForm() {
        return "uploadMach-form";
    }

    @GetMapping("/deleteAll")
    public String deleteAll() {
        log.debug("deleteAll()");
        machineService.deleteAllMachine();
        log.debug("deleteAll(...)");
        return "redirect:/machines";

    }

    @PostMapping(value = "/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        log.debug("importMachines()");
        if (file.isEmpty()) {
            log.debug("Please select file to upload");
            return "redirect/machines";
        }
        List<Machine> machines = importMachine.importExcelMachineData(file);
        machines.forEach((machine) -> {
            machineService.createMachine(machine);
        });
        log.debug("importMachines(...) ");
        return "redirect:/machines";
    }
}
