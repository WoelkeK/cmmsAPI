package pl.medos.cmmsApi.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pl.medos.cmmsApi.model.Notification;
import pl.medos.cmmsApi.service.NotificationService;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("api/notifications")
@Tag(name = "AwizacjeAPI")
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/create")
    Notification createNotification(@RequestBody Notification notification) {
        log.debug("createNotification()");
        return notificationService.createNotification(notification);
    }

    @GetMapping("/findById")
    Notification findNotificationById(@RequestParam Long id) {
        log.debug("findNotificationById()");
        return notificationService.findNotificationById(id);
    }

    @PutMapping("/update")
    Notification updateNotification(@RequestBody Notification notification, Long id) {
        log.debug("updateNotification()");
        return notificationService.updateNotification(notification, id);
    }

    @DeleteMapping("/delete")
    void deleteNotification(@RequestParam Long id) {
        log.debug("deleteNotification()");
        notificationService.deleteNotification(id);
    }

    @GetMapping("/getList")
    List<Notification> findAllNotifications() {
        log.debug("findallNotifications()");
        return notificationService.getAllNotifications();
    }

    @DeleteMapping("/deleteAll")
    void deleteAll(){
        log.debug("deleteall()");
        notificationService.deleteAll();
    }
}
