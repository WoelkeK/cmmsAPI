package pl.medos.cmmsApi.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pl.medos.cmmsApi.model.Notification;
import pl.medos.cmmsApi.service.NotificationService;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/")
@Tag(name = "AwizacjeAPI")
@Slf4j
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("notifications/create")
    Notification createNotification(@RequestBody Notification notification) {
        log.info("createNotification()");
        return notificationService.createNotification(notification);
    }

    @GetMapping("notifications/findById")
    Notification findNotificationById(@RequestParam Long id) {
        log.info("findNotificationById()");
        return notificationService.findNotificationById(id);
    }

    @PutMapping("notifications/update")
    Notification updateNotification(@RequestBody Notification notification, Long id) {
        log.info("updateNotification()");
        return notificationService.updateNotification(notification, id);
    }

    @DeleteMapping("notifications/delete")
    void deleteNotification(@RequestParam Long id) {
        log.info("deleteNotification()");
        notificationService.deleteNotification(id);
    }

    @GetMapping("notifications/getList")
    List<Notification> findAllNotifications() {
        log.info("findallNotifications()");
        return notificationService.getAllNotifications();
    }

    @DeleteMapping("/deleteAll")
    void deleteAll(){
        log.info("deleteall()");
        notificationService.deleteAll();

    }



}
