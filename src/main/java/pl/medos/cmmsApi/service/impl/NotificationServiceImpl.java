package pl.medos.cmmsApi.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.model.Notification;
import pl.medos.cmmsApi.repository.NotificationRepository;
import pl.medos.cmmsApi.repository.entity.NotificationEntity;
import pl.medos.cmmsApi.service.NotificationService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final ModelMapper modelMapper;
    private final NotificationRepository notificationRepository;

    @Override
    public Notification createNotification(Notification notification) {
        log.info("createNotification()");
        NotificationEntity notificationEntity = notificationRepository.save(modelMapper.map(notification, NotificationEntity.class));
        return modelMapper.map(notificationEntity, Notification.class);
    }

    @Override
    public Notification findNotificationById(Long id) {
        log.info("findNotificationById()");
        return modelMapper.map(notificationRepository.findById(id), Notification.class);
    }

    @Override
    public Notification updateNotification(Notification notification, Long id) {
        log.info("updateNotification()");
        findNotificationById(id);
        NotificationEntity notificationEntity = modelMapper.map(notification, NotificationEntity.class);
        notificationEntity.setId(id);
        return modelMapper.map(notificationRepository.save(notificationEntity), Notification.class);
    }

    @Override
    public void deleteNotification(Long id) {
        log.info("deleteNotification()");
        notificationRepository.deleteById(id);
    }

    @Override
    public List<Notification> getAllNotifications() {
        log.info("getNotifications()");
        List<NotificationEntity> notificationEntities = notificationRepository.findAll();
        return notificationEntities.stream()
                .map(e -> modelMapper.map(e, Notification.class)).toList();
    }

    @Override
    public List<Notification> findNotifiByQuery(String query) {
        log.info("findNotificationByQuery()");
        List<NotificationEntity> repositoryByQuery = notificationRepository.searchNotificationsByQuery(query);
        return repositoryByQuery.stream().map(
                        notificationEntity -> modelMapper.map(notificationEntity, Notification.class))
                .toList();
    }

    @Override
    public Page<Notification> findPageNotifications(int pageNo, int size, String sortField, String sortDirection) {
        log.info("findPagesNotifications()");
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, size, sort);
        Page<NotificationEntity> notificationEntities = notificationRepository.findAll(pageable);
        return notificationEntities.map(NotificationEntity -> modelMapper.map(NotificationEntity, Notification.class));
    }

    @Override
    public List<Notification> findSortNotifications(String sortField, String sortDirection) {
        log.info("findAllSorted()");
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        List<NotificationEntity> notificationEntities = notificationRepository.findAll(sort);
        return notificationEntities.stream().map(NotificationEntity->modelMapper.map(NotificationEntity,Notification.class)).toList();

    }
}
