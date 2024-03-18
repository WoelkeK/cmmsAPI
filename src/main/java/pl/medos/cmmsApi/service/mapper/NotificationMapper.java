package pl.medos.cmmsApi.service.mapper;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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
public class NotificationMapper {

    private static final Logger LOGGER = Logger.getLogger(NotificationMapper.class.getName());
    private NotificationEntity notificationEntity;

    public List<Notification> listModels(List<NotificationEntity> notificationEntities) {
        LOGGER.info("list()" + notificationEntities);

        List<Notification> notificationsModels = notificationEntities.stream()
                .map(this::entityToModel)
                .collect(Collectors.toList());
        return notificationsModels;
    }

    public Notification entityToModel(NotificationEntity notificationEntity) {
        LOGGER.info("entityToModel" + notificationEntity);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(notificationEntity, Notification.class);
    }

    public NotificationEntity modelToEntity(Notification notification) {
        LOGGER.info("modelToEntity()" + notification);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(notification, NotificationEntity.class);
    }

    public Page<Notification> entititesToModelsPage(Page<NotificationEntity> notificationEntityPage) {
        LOGGER.info("entitiesToModelsPage()");
        ModelMapper modelMapper = new ModelMapper();
        Page<Notification> notifications = notificationEntityPage.map(NotificationEntity -> modelMapper.map(NotificationEntity, Notification.class));
        LOGGER.info("entitiesToModelsPage(...)");
        return notifications;
    }
}
