package pl.medos.cmmsApi.service.mapper;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import pl.medos.cmmsApi.model.Machine;
import pl.medos.cmmsApi.model.Notification;
import pl.medos.cmmsApi.repository.entity.MachineEntity;
import pl.medos.cmmsApi.repository.entity.NotificationEntity;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
@Slf4j
public class NotificationMapper {

    private NotificationEntity notificationEntity;

    public List<Notification> listModels(List<NotificationEntity> notificationEntities) {
        log.debug("list()" + notificationEntities);

        List<Notification> notificationsModels = notificationEntities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());
        return notificationsModels;
    }

    public Notification entityToModel(NotificationEntity notificationEntity) {
        log.debug("entityToModel" + notificationEntity);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(notificationEntity, Notification.class);
    }

    public NotificationEntity modelToEntity(Notification notification) {
        log.debug("modelToEntity()" + notification);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(notification, NotificationEntity.class);
    }

    public Page<Notification> entititesToModelsPage(Page<NotificationEntity> notificationEntityPage) {
        log.debug("entitiesToModelsPage()");
        ModelMapper modelMapper = new ModelMapper();
        Page<Notification> notifications = notificationEntityPage.map(NotificationEntity -> modelMapper.map(NotificationEntity, Notification.class));
        log.debug("entitiesToModelsPage(...)");
        return notifications;
    }
}
