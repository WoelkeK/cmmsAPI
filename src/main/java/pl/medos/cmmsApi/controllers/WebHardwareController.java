package pl.medos.cmmsApi.controllers;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.exception.HardwareNotFoundException;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.model.Hardware;
import pl.medos.cmmsApi.repository.HardwareRepository;
import pl.medos.cmmsApi.service.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/hardwares")
@SessionAttributes(names = {"hardwares"})
public class WebHardwareController {

    private static final Logger LOGGER = Logger.getLogger(WebHardwareController.class.getName());
    private HardwareService hardwareService;
    private ExportService exportService;
    private ImportService importService;
    private RaportService raportService;
    private final HardwareRepository hardwareRepository;


    public WebHardwareController(HardwareService hardwareService, ExportService exportService, ImportService importService, RaportService raportService,
                                 HardwareRepository hardwareRepository) {
        this.hardwareService = hardwareService;
        this.exportService = exportService;
        this.importService = importService;
        this.raportService = raportService;
        this.hardwareRepository = hardwareRepository;
    }

    @GetMapping("/list")
    public String listViewAll(Model model) {
        LOGGER.info("listViewAll()");
        List<Hardware> hardwares = hardwareService.listAll();
        model.addAttribute("hardwares", hardwares);
        LOGGER.info("listViewAll(...)");
        return "list-hardware";
    }

    @GetMapping("/sort/")
    public String listViewAllSorted(@RequestParam("sortField") String sortField,
                                    @RequestParam("sortDir") String direction,
                                    Model model) {
        LOGGER.info("listViewAll()");
        List<Hardware> hardwares = hardwareService.findAllSorted(direction, sortField);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", direction);
        model.addAttribute("reverseSortDir", direction.equals("asc") ? "desc" : "asc");
        model.addAttribute("hardwares", hardwares);
        LOGGER.info("listViewAll(...)");
        return "sort-hardware";
    }

    @GetMapping
    public String listView(Model model){
        LOGGER.info("listView()");
        return findHardwarePage(1, "inventoryNo", "asc", model);
//        return listViewAllSorted("inventoryNo", "desc", model);
    }

    @GetMapping(value = "/page/{pageNo}")
    public String findHardwarePage(@PathVariable(value = "pageNo") int pageNo,
                                   @RequestParam("sortField") String sortField,
                                   @RequestParam("sortDir") String sortDirection,
                                   Model model) {

        LOGGER.info("findHardwarePage()");
        int size = 10;
        Page<Hardware> hardwaresPage = hardwareService.pagesHardware(pageNo, size, sortField, sortDirection) ;
        List<Hardware> hardwares = hardwaresPage.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", hardwaresPage.getTotalPages());
        model.addAttribute("totalItems", hardwaresPage.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDirection);
        model.addAttribute("reverseSortDir", sortDirection.equals("asc") ? "desc" : "asc");

        model.addAttribute("hardwares", hardwares);
        LOGGER.info("pageing(...)");
        return "main-hardware";
    }

    @GetMapping("/create")
    public String createView(Model model) {
        LOGGER.info("createView()");
        model.addAttribute("hardware", new Hardware());
        LOGGER.info("createView(...)");
        return "create-hardware";
    }

    @PostMapping("/create")
    public String createHardware(
            @ModelAttribute(name = "hardware") Hardware hardware) {
        LOGGER.info("createHardware()" + hardware.getInstallDate());
        Hardware savedHardware = hardwareService.create(hardware);
        LOGGER.info("createHardware(...)" + savedHardware);
        return "redirect:/hardwares";
    }

    @GetMapping(value = "/read/{id}")
    public String findHardware(@PathVariable(name = "id") Long id,
                               Model model) throws HardwareNotFoundException {
        LOGGER.info("findHardware(" + id + ")");
        Hardware findHardware = hardwareService.read(id);
        model.addAttribute("hardware", findHardware);
        LOGGER.info("findHardware(...)" + findHardware);
        return "view-hardware";
    }

    @GetMapping("/update/{id}")
    public String updateView(@PathVariable(name = "id") Long id, Model model) throws HardwareNotFoundException {
        LOGGER.info("updateView(" + id + ")");
        Hardware hardware = hardwareService.read(id);
        model.addAttribute("hardware", hardware);
        LOGGER.info("updateView(...)" + hardware.getId());
        return "update-hardware";
    }

    @PostMapping("/update")
    public String updateHardware(
            @ModelAttribute(name = "hardware") Hardware hardware) throws HardwareNotFoundException {
        LOGGER.info("updateHardware(emp)" + hardware.getId());
        Hardware updatedHardware = hardwareService.update(hardware);
        LOGGER.info("updateHardware(...)");
        return "redirect:/hardwares";
    }

    @GetMapping("/delete/{id}")
    public String deleteHardware(@PathVariable(name = "id") Long id) {
        LOGGER.info("deleteHardware(" + id + ")");
        hardwareService.delete(id);
        LOGGER.info("deleteHardware(...)");
        return "redirect:/hardwares";
    }
    @GetMapping("/deleteAll")
    public String deleteAllHardware(){
        LOGGER.info("deleteAllHardware()");
        hardwareService.deleteAll();
        LOGGER.info("deleteAllHardware(...)");
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
        LOGGER.info("export()");
        response.setContentType("application/octet-stream");
        DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateTimeFormat.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=hardware" + currentDateTime + ".xlsx";

        response.setHeader(headerKey, headerValue);
        exportService.excelHardwaresModelGenerator(hardwares);
        exportService.generateExcelHardwareFile(response);
        response.flushBuffer();
        LOGGER.info("export(...)");
    }

    @GetMapping(value = "/file")
    public String showUploadForm() {
        return "uploadHard-form";
    }

    @PostMapping(value = "/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {

        LOGGER.info("importHardwares()");
        if (file.isEmpty()) {
            LOGGER.info("Proszę wybrać plik do importu");
            return "redirect/hardwares";
        }

        List<Hardware> hardwares = importService.importExcelHardwareData(file);

        hardwares.forEach((hardware) -> {
            hardwareService.create(hardware);
        });
        LOGGER.info("importHardwares(...) ");
        return "redirect:/hardwares";
    }

    @GetMapping(value = "/search/query")
    public String findHardwareByQuery(
            @RequestParam(value = "query") String query,
//            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            Model model) throws IOException {
        int pageSize=10;
        int pageNo=1;
        String sortField="name";
        String sortDir="desc";

        LOGGER.info("findPage()");
        Page<Hardware> hardwarePage =hardwareService.findHardwarePageByQuery(pageNo, pageSize, sortField, sortDir, query);
        List<Hardware> hardware = hardwarePage.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", hardwarePage.getTotalPages());
        model.addAttribute("totalItems", hardwarePage.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("hardwares", hardware);
        return "main-hardware";
    }
}
