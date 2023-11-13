package pl.medos.cmmsApi.controllers;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.model.Notification;
import pl.medos.cmmsApi.service.ImageService;
import pl.medos.cmmsApi.service.NotificationService;
import pl.medos.cmmsApi.service.RaportService;


import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/notifications")
@SessionAttributes(names = {"images", "notifications"})
@RequiredArgsConstructor
@Slf4j
public class NotificationWebcontroller {

    private final NotificationService notificationService;

    private final ImageService imageService;

    private final RaportService raportService;


    @GetMapping("/read/{id}")
    public String findNotificationById(@PathVariable(name = "id") Long id, Model model) {
        log.info("findNotificationById() " + id);
        Notification notificationById = notificationService.findNotificationById(id);
        model.addAttribute("notification", notificationById);
        return "view-notification.html";
    }

    @GetMapping("/update/{id}")
    public String updateView(@PathVariable(name = "id") Long id, Model model) {
        log.info("update()");
        Notification notificationById = notificationService.findNotificationById(id);
        model.addAttribute("notification", notificationById);
        return "update-notification.html";
    }

    @PostMapping("/update")
    public String updateNotification(@ModelAttribute(name = "notification") Notification notification) {
        log.info("updateNotification()");
        notificationService.updateNotification(notification, notification.getId());
        return "redirect:/notifications";
    }

    @GetMapping("/list")
    public String findAllNotifications(ModelMap modelMap) {
        log.info("findallNotifications()");
        List<Notification> allNotifications = notificationService.getAllNotifications();
        modelMap.addAttribute("notifications", allNotifications);

        Map<Long, String> jobBase64Images = new HashMap<>();
        for (Notification notification : allNotifications) {
            jobBase64Images.put(notification.getId(), Base64.getEncoder().encodeToString(notification.getResizedImage()));
        }
        modelMap.addAttribute("images", jobBase64Images);
        return "main-notification.html";
    }

    @GetMapping
    public String listView(Model model){
        log.info("listView()");
//        return findHardwarePage(1, "inventoryNo", "asc", model);
        return findPagesNotifications(1,"visitDate", "desc", model);
    }

    @GetMapping("/sort/")
    public String listViewAllSorted(@RequestParam("sortField") String sortField,
                                    @RequestParam("sortDir") String sortDir,
                                    Model model) {
        log.info("listViewAll()");
        List<Notification> notifications = notificationService.findSortNotifications(sortField, sortDir);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        return "list-notifications.html";
    }

    @GetMapping(value = "/page/{pageNo}")
    public String findPagesNotifications(@PathVariable(name = "pageNo") int pageNo,
                                         @RequestParam(name = "sortField") String sortField,
                                         @RequestParam(name = "sortDir") String sortDir,
                                         Model model) {
        log.info("findPagesNotifications()");
        int size =10;
        Page<Notification> notifications = notificationService.findPageNotifications(pageNo, size, sortField, sortDir);
        List<Notification> notificationList = notifications.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", notifications.getTotalPages());
        model.addAttribute("totalItems", notifications.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("notifications", notificationList);

        Map<Long, String> jobBase64Images = new HashMap<>();
        for (Notification notification :notificationList) {
            jobBase64Images.put(notification.getId(), Base64.getEncoder().encodeToString(notification.getResizedImage()));
        }
        model.addAttribute("images", jobBase64Images);
        return "main-notification.html";
    }

    @GetMapping("/create")
    public String createView(Model model) {
        log.info("createView");
//        String employeesUri = externalServerURL + "/employees";
//        RestTemplate restTemplate = new RestTemplate();
//        Object[] employeesObjects = restTemplate.getForObject(employeesUri, Object[].class);
//        List<Object> employees = Arrays.asList(employeesObjects);
//        model.addAttribute("employees", employees);
        model.addAttribute("notification", new Notification());
        return "create-notification.html";
    }

    @PostMapping("/create")
    public String createNotification(@ModelAttribute(name = "notification") Notification notification, MultipartFile image) throws IOException {
        log.info("createNotification()");
        notificationService.createNotification(imageService.prepareImage(notification, image));
        return "redirect:/notifications";
    }

    @GetMapping("delete/{id}")
    public String deleteNotification(@PathVariable Long id) {
        log.info("deleteNotification()");
        notificationService.deleteNotification(id);
        return "redirect:/notifications";
    }

    @GetMapping(value = "/downloadfile")
    public void downloadFile(@Param(value = "id") Long id, Model model, HttpServletResponse response) throws IOException {
        Notification notificationById = notificationService.findNotificationById(id);
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename = " + notificationById.getVisitDate() + ".jpg";
        response.setHeader(headerKey, headerValue);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(notificationById.getOriginalImage());
        outputStream.close();
    }

    @GetMapping("/search/{query}")
    public String searchByQuery(@RequestParam(name = "query") String query, Model model) {
        log.info("searchByQuery()");
        List<Notification> notifications = notificationService.findNotifiByQuery(query);
        model.addAttribute("notifications", notifications);
        return "main-notification.html";
    }

    @PostMapping("/exportPdf")
    public void generateReport(HttpServletResponse response, Notification notification) throws JRException, IOException {

        response.setContentType("application/x-download");
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"hardware_" + notification.getVisitDate() + ".pdf\""));
        OutputStream out = response.getOutputStream();
        raportService.exportReport(notification, out);
    }
}
