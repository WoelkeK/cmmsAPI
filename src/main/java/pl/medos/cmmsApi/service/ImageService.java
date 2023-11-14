package pl.medos.cmmsApi.service;

import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.model.Notification;

import java.io.IOException;

public interface ImageService {

    byte[] simpleResizeImage(byte[] originalImage, int targetWidth) throws IOException;

    byte[] multipartToByteArray(MultipartFile multipartFile) throws IOException;

    byte[] imageToByteArray() throws IOException;

    Notification prepareImage(Notification notification, MultipartFile image) throws IOException;

}
