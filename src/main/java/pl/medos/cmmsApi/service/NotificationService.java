package pl.medos.cmmsApi.service;

import org.springframework.data.domain.Page;
import pl.medos.cmmsApi.model.Notification;

import java.util.List;

public interface NotificationService {

    Notification createNotification(Notification notification);
    Notification findNotificationById(Long id);
    Notification updateNotification(Notification notification, Long id);
    void deleteNotification(Long id);
    List<Notification> getAllNotifications();

    List<Notification> findNotifiByQuery(String query);
    Page<Notification> findPageNotifications(int pageNo, int size, String sortField, String sortDirection);
    List<Notification> findSortNotifications(String sortField, String sortDirection);
    Page<Notification> findNotificationPageByQuery(int pageNo, int pageSize, String sortField, String sortDir, String query);
}
