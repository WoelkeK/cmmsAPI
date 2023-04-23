package pl.medos.cmmsApi.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface ImageService {

    byte[] simpleResizeImage(byte[] originalImage, int targetWidth) throws IOException;

    byte[] multipartToByteArray(MultipartFile multipartFile) throws IOException;

    byte[] imageToByteArray() throws IOException;

}
