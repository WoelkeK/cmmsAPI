package pl.medos.cmmsApi.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.model.*;
import pl.medos.cmmsApi.service.ExportService;
import pl.medos.cmmsApi.service.NotificationService;
import pl.medos.cmmsApi.service.RaportService;
import pl.medos.cmmsApi.util.imports.ImportNotification;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

@Controller
@RequestMapping("/awizacje")
@SessionAttributes(names = {"awizacje"})
@Slf4j
public class NotificationWebcontroller {

    private NotificationService notificationService;
    private RaportService raportService;
    private ExportService exportService;
    private ImportNotification importNotification;

    public NotificationWebcontroller(NotificationService notificationService, RaportService raportService, ExportService exportService, ImportNotification importNotification) {
        this.notificationService = notificationService;
        this.raportService = raportService;
        this.exportService = exportService;
        this.importNotification = importNotification;
    }

    @GetMapping("/read/{id}")
    public String findNotificationById(@PathVariable(name = "id") Long id,
                                       @RequestParam(name = "pageNo") int pageNo,
                                       Model model) {
        log.debug("findNotificationById() " + id);
        Notification notificationById = notificationService.findNotificationById(id);
        model.addAttribute("notification", notificationById);
        model.addAttribute("pageNo", pageNo);
        return "view-notification.html";
    }

    @GetMapping("/update/{id}")
    public String updateView(@PathVariable(name = "id") Long id, Model model) {
        log.debug("update()");
        Notification notificationById = notificationService.findNotificationById(id);
        model.addAttribute("notification", notificationById);
        return "update-notification.html";
    }

    @PostMapping("/update")
    public String updateNotification(@ModelAttribute(name = "notification") Notification notification) {
        log.debug("updateNotification()");
        notificationService.updateNotification(notification, notification.getId());
        return "redirect:/awizacje";
    }

    @GetMapping("/list")
    public String findAllNotifications(ModelMap modelMap) {
        log.debug("findallNotifications()");
        List<Notification> allNotifications = notificationService.getAllNotifications();
        modelMap.addAttribute("notifications", allNotifications);
        return "main-notification.html";
    }

    @GetMapping
    public String listView(Model model) {
        log.debug("listView()");
        return findPagesNotifications(1, "visitDate", "asc", model);
    }

    @GetMapping("/sort/")
    public String listViewAllSorted(@RequestParam("sortField") String sortField,
                                    @RequestParam("sortDir") String sortDir,
                                    Model model) {
        log.debug("listViewAll()");
        List<Notification> notifications = notificationService.findSortNotifications(sortField, sortDir);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        return "list-notifications.html";
    }

    @GetMapping(value = "/page/{pageNo}")
    public String findPagesNotifications(@PathVariable(name = "pageNo") int pageNo,
                                         @RequestParam(name = "sortField", defaultValue = "visitDate") String sortField,
                                         @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir,
                                         Model model) {
        log.debug("findPagesNotifications()");
        int size = 10;
        Page<Notification> notifications = notificationService.findPageNotifications(pageNo, size, sortField, sortDir);
        List<Notification> notificationList = notifications.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", notifications.getTotalPages());
        model.addAttribute("totalItems", notifications.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("notifications", notificationList);
        return "main-notification.html";
    }

    @GetMapping("/create")
    public String createView(Model model) {
        log.debug("createView");
        model.addAttribute("notification", new Notification());
        return "create-notification.html";
    }

    @PostMapping("/create")
    public String createNotification(@Valid @ModelAttribute(name = "notification") Notification notification,
                                     BindingResult result,
                                     Model model) throws IOException {
        log.debug("createNotification()");
        if (result.hasErrors()) {
            log.debug("create: result has erorr()" + result.getFieldError());
            model.addAttribute("notification", notification);
            return "create-notification";
        }
        notificationService.createNotification(notification);
        return "redirect:/awizacje";
    }

    @GetMapping("delete/{id}")
    public String deleteNotification(@PathVariable Long id) {
        log.debug("deleteNotification()");
        notificationService.deleteNotification(id);
        return "redirect:/awizacje";
    }

    @PostMapping("/exportPdf")
    public void generateReport(HttpServletResponse response, Notification notification) throws JRException, IOException {
        log.debug("exportPdf()" + notification.getId());
        response.setContentType("application/x-download");
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"awizacja_" + notification.getVisitDate() + ".pdf\""));
        OutputStream out = response.getOutputStream();
        raportService.exportReport(notification, out);
    }

    @GetMapping(value = "/search/query")
    public String findNotifyByQuery(
            @RequestParam(value = "query") String query,
            Model model) throws IOException {
        int pageSize = 10;
        int pageNo = 1;
        String sortField = "visitDate";
        String sortDir = "asc";
        log.debug("findPage()");
        Page<Notification> notificationPage = notificationService.findNotificationPageByQuery(pageNo, pageSize, sortField, sortDir, query);
        List<Notification> notifications = notificationPage.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", notificationPage.getTotalPages());
        model.addAttribute("totalItems", notificationPage.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("notifications", notifications);
        return "main-notification";
    }

    @GetMapping(value = "/export")
    public void exportNotification(HttpServletResponse response, Model model) throws Exception {
        log.debug("export()");
        List<Notification>notifications = notificationService.getAllNotifications();
        response.setContentType("application/octet-stream");
        DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateTimeFormat.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=awizacje" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        exportService.excelNotificationModelGenerator(notifications);
        exportService.generateExcelNotificationFile(response);
        response.flushBuffer();
        log.debug("export(...)");
    }

    @GetMapping(value = "/file")
    public String showUploadForm() {
        return "uploadNotifi-form";
    }

    @PostMapping(value = "/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        log.debug("importNotifications()");
        if (file.isEmpty()) {
            log.debug("Proszę wybrać plik do importu");
            return "redirect/awizacje";
        }
        List<Notification> notifications = importNotification.importNotificationFromXLS(file);
        notifications.forEach(notificationService::createNotification);
        log.debug("importNotifications(...) ");
        return "redirect:/awizacje";
    }
}
