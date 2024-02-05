package pl.medos.cmmsApi.util.imports;

import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.model.Notification;

import java.io.IOException;
import java.util.List;

public interface ImportNotification {
    List<Notification> importNotificationFromXLS(MultipartFile file) throws IOException;
}
