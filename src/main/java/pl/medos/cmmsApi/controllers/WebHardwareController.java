package pl.medos.cmmsApi.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.exception.HardwareNotFoundException;
import pl.medos.cmmsApi.model.Hardware;
import pl.medos.cmmsApi.repository.HardwareRepository;
import pl.medos.cmmsApi.service.*;
import pl.medos.cmmsApi.util.imports.ImportHardware;
import pl.medos.cmmsApi.util.imports.ImportHardwareFromXls;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/hardwares")
@SessionAttributes(names = {"hardwares"})
@Slf4j
public class WebHardwareController {

    private HardwareService hardwareService;
    private ExportService exportService;
    private ImportHardware importHardware;
    private RaportService raportService;
    private HardwareRepository hardwareRepository;

    public WebHardwareController(HardwareService hardwareService, ExportService exportService, ImportHardware importHardware, RaportService raportService, HardwareRepository hardwareRepository) {
        this.hardwareService = hardwareService;
        this.exportService = exportService;
        this.importHardware = importHardware;
        this.raportService = raportService;
        this.hardwareRepository = hardwareRepository;
    }

    @GetMapping
    public String listViewAll( @RequestParam(value = "sortField", defaultValue = "inventoryNo") String sortField,
                               @RequestParam(value = "sortDir",defaultValue = "asc") String direction,
                                    Model model) {
        log.debug("listViewAll()");
        List<Hardware> hardwares = hardwareService.findAllSorted(direction, sortField);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", direction);
        model.addAttribute("reverseSortDir", direction.equals("asc") ? "desc" : "asc");
        model.addAttribute("hardwares", hardwares);
        log.debug("listViewAll(...)");
        return "main-hardware2";
    }

    @GetMapping("/sort/")
    public String listViewAllSorted(@RequestParam("sortField") String sortField,
                                    @RequestParam("sortDir") String direction,
                                    Model model) {
        log.debug("listViewAll()");
        List<Hardware> hardwares = hardwareService.findAllSorted(direction, sortField);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", direction);
        model.addAttribute("reverseSortDir", direction.equals("asc") ? "desc" : "asc");
        model.addAttribute("hardwares", hardwares);
        log.debug("listViewAll(...)");
        return "sort-hardware";
    }

    @GetMapping("/create")
    public String createView(Model model) {
        log.debug("createView()");
        model.addAttribute("hardware", new Hardware());
        log.debug("createView(...)");
        return "create-hardware";
    }

    @PostMapping("/create")
    public String createHardware(
            @ModelAttribute(name = "hardware") Hardware hardware) {
        log.debug("createHardware()" + hardware.getInstallDate());
        Hardware savedHardware = hardwareService.create(hardware);
        log.debug("createHardware(...)" + savedHardware);
        return "redirect:/hardwares";
    }

    @GetMapping(value = "/read/{id}")
    public String findHardware(@PathVariable(name = "id") Long id,
                               Model model) throws HardwareNotFoundException {
        log.debug("findHardware(" + id + ")");
        Hardware findHardware = hardwareService.read(id);
        model.addAttribute("hardware", findHardware);
        log.debug("findHardware(...)" + findHardware);
        return "view-hardware2";
    }

    @GetMapping("/update/{id}")
    public String updateView(@PathVariable(name = "id") Long id, Model model) throws HardwareNotFoundException {
        log.debug("updateView(" + id + ")");
        Hardware hardware = hardwareService.read(id);
        model.addAttribute("hardware", hardware);
        log.debug("updateView(...)" + hardware.getId());
        return "update-hardware";
    }

    @PostMapping("/update")
    public String updateHardware(
            @ModelAttribute(name = "hardware") Hardware hardware) throws HardwareNotFoundException {
        log.debug("updateHardware(emp)" + hardware.getId());
        Hardware updatedHardware = hardwareService.update(hardware);
        log.debug("updateHardware(...)");
        return "redirect:/hardwares";
    }

    @GetMapping("/delete/{id}")
    public String deleteHardware(@PathVariable(name = "id") Long id) {
        log.debug("deleteHardware(" + id + ")");
        hardwareService.delete(id);
        log.debug("deleteHardware(...)");
        return "redirect:/hardwares";
    }
    @GetMapping("/deleteAll")
    public String deleteAllHardware(){
        log.debug("deleteAllHardware()");
        hardwareService.deleteAll();
        log.debug("deleteAllHardware(...)");
        return "redirect:/hardwares";
    }

    @PostMapping("/exportPdf")
    public void generateReport(HttpServletResponse response, Hardware hardware) throws JRException, IOException {
        response.setContentType("application/x-download");
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"hardware_"+hardware.getInventoryNo()+".pdf\""));
        OutputStream out = response.getOutputStream();
        raportService.exportReport(hardware, out);
    }

    @GetMapping(value = "/export")
    public void exportHardwares(@ModelAttribute(name = "hardwares") List<Hardware> hardwares,
                               HttpServletResponse response, Model model) throws Exception {
        log.debug("export()");
        hardwares=hardwareService.listAll();
        response.setContentType("application/octet-stream");
        DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateTimeFormat.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=hardware" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        exportService.excelHardwaresModelGenerator(hardwares);
        exportService.generateExcelHardwareFile(response);
        response.flushBuffer();
        log.debug("export(...)");
    }

    @GetMapping(value = "/file")
    public String showUploadForm() {
        return "uploadHard-form";
    }

    @PostMapping(value = "/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {

        log.debug("importHardwares()");
        if (file.isEmpty()) {
            log.debug("Proszę wybrać plik do importu");
            return "redirect/hardwares";
        }
        List<Hardware> hardwares = importHardware.importHardware(file);
        hardwares.forEach((hardware) -> {
            hardwareService.create(hardware);
        });
        log.debug("importHardwares(...) ");
        return "redirect:/hardwares";
    }

    @GetMapping(value = "/search/query")
    public String findHardwareByQuery(
            @RequestParam(value = "query") String query,
            Model model)  {
        int pageSize=1000;
        int pageNo=1;
        String sortField="name";
        String sortDir="desc";
        log.debug("findPage()");
        Page<Hardware> hardwarePage =hardwareService.findHardwarePageByQuery(pageNo, pageSize, sortField, sortDir, query);
        List<Hardware> hardware = hardwarePage.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", hardwarePage.getTotalPages());
        model.addAttribute("totalItems", hardwarePage.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("hardwares", hardware);
        return "main-hardware2";
    }
}
