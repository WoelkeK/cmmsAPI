package pl.medos.cmmsApi.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.model.Hardware;
import pl.medos.cmmsApi.model.Notification;
import pl.medos.cmmsApi.repository.NotificationRepository;
import pl.medos.cmmsApi.repository.entity.HardwareEntity;
import pl.medos.cmmsApi.repository.entity.NotificationEntity;
import pl.medos.cmmsApi.service.NotificationService;
import pl.medos.cmmsApi.service.mapper.NotificationMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationMapper notificationMapper;
    private final NotificationRepository notificationRepository;

    @Override
    public Notification createNotification(Notification notification) {
        log.info("createNotification()");
        NotificationEntity savedNotification = notificationRepository.save(notificationMapper.modelToEntity(notification));
        return notificationMapper.entityToModel(savedNotification);
    }

    @Override
    public Notification findNotificationById(Long id) {
        log.info("findNotificationById()");
        return notificationMapper.entityToModel(notificationRepository.findById(id).get());
    }

    @Override
    public Notification updateNotification(Notification notification, Long id) {
        log.info("updateNotification()");
        findNotificationById(id);
        NotificationEntity notificationEntity = notificationMapper.modelToEntity(notification);
        notificationEntity.setId(id);
        return notificationMapper.entityToModel(notificationRepository.save(notificationEntity));
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
        return notificationMapper.listModels(notificationEntities);
    }

    @Override
    public List<Notification> findNotifiByQuery(String query) {
        log.info("findNotificationByQuery()");
        List<NotificationEntity> repositoryByQuery = notificationRepository.searchNotificationsByQuery(query);
        return notificationMapper.listModels(repositoryByQuery);
    }

    @Override
    public Page<Notification> findPageNotifications(int pageNo, int size, String sortField, String sortDirection) {
        log.info("findPagesNotifications()");
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, size, sort);
        Page<NotificationEntity> notificationEntities = notificationRepository.findAll(pageable);
        return notificationMapper.entititesToModelsPage(notificationEntities);
    }

    @Override
    public List<Notification> findSortNotifications(String sortField, String sortDirection) {
        log.info("findAllSorted()");
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        List<NotificationEntity> notificationEntities = notificationRepository.findAll(sort);
        return notificationMapper.listModels(notificationEntities);
    }

    @Override
    public Page<Notification> findNotificationPageByQuery(int pageNo, int pageSize, String sortField, String sortDir, String query) {
        log.info("findNotificationPageByQuery()");
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        Page<NotificationEntity> hardwareEntityPage = notificationRepository.findByQueryPagable(query, pageable);
        Page<Notification> notifications = notificationMapper.entititesToModelsPage(hardwareEntityPage);
        log.info("findHardwarePageByQuery(...)");
        return notifications;
    }
}
