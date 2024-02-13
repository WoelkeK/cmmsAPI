package pl.medos.cmmsApi.service.impl;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.model.Notification;
import pl.medos.cmmsApi.model.Pass;
import pl.medos.cmmsApi.service.ImageService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

@Component
public class ImageServiceImpl implements ImageService {

    private static final Logger LOGGER = Logger.getLogger(ImageServiceImpl.class.getName());

    @Override
    public byte[] simpleResizeImage(byte[] originalImage, int targetWidth) throws IOException {
        BufferedImage bufferedImage = toBufferedImage(originalImage);
        BufferedImage resizedBufferedImage = Scalr.resize(bufferedImage, targetWidth);
        byte[] bytesResImage = toByteArray(resizedBufferedImage, "jpg");
        return bytesResImage;
    }

    @Override
    public byte[] multipartToByteArray(MultipartFile multipartFile) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();
        BufferedImage bfImage = ImageIO.read(inputStream);
        byte[] bytesImage = toByteArray(bfImage, "jpg");
        inputStream.close();
        bfImage.flush();
        return bytesImage;
    }

    @Override
    public byte[] imageToByteArray() {
        LOGGER.info("imageToByteArray()");
        URL resource = getClass().getResource("/image.jpg");
        BufferedImage bf = null;
        try {
            bf = ImageIO.read(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        byte[] defaultImage = new byte[0];
        try {
            defaultImage = toByteArray(bf, "jpg");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        bf.flush();
        return defaultImage;
    }

    @Override
    public Notification prepareImage(Notification notification, MultipartFile image) throws IOException {
        return null;
    }

//    @Override
//    public Notification prepareImage(Notification notification, MultipartFile image) throws IOException {
//        LOGGER.info("prepareImage()");
//
//        if (image.getSize() == 0) {
//            LOGGER.info("default image");
//            byte[] bytes = imageToByteArray();
//            notification.setResizedImage(bytes);
//            notification.setOriginalImage(bytes);
//        } else {
//            LOGGER.info("multipart file present");
//            byte[] orginalImage = multipartToByteArray(image);
//            byte[] resizeImage = simpleResizeImage(orginalImage, 300);
//            byte[] resizeMaxImage = simpleResizeImage(orginalImage, 800);
//            notification.setResizedImage(resizeImage);
//            notification.setOriginalImage(resizeMaxImage);
//        }
//        LOGGER.info("image prepared");
//        return notification;
//    }

    @Override
    public Pass prepareImage(Pass pass, MultipartFile image) throws IOException {
        LOGGER.info("prepareImage()");

        if (image.getSize() == 0 && pass.getOriginalImage()==null) {
            LOGGER.info("default image");
            byte[] bytes = imageToByteArray();
            pass.setResizedImage(bytes);
            pass.setOriginalImage(bytes);
        } else {
            LOGGER.info("multipart file present");
            if (image.isEmpty() || image.getBytes()==null) {
                return pass;
            } else {
                LOGGER.info("procesed Image() ");
                byte[] orginalImage = multipartToByteArray(image);
                byte[] resizeImage = simpleResizeImage(orginalImage, 100);
                byte[] resizeMaxImage = simpleResizeImage(orginalImage, 300);
                pass.setOriginalImage(orginalImage);
                pass.setResizedImage(resizeMaxImage);
            }
        }
        LOGGER.info("image prepared");
        return pass;
    }


    byte[] toByteArray(BufferedImage bi, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, format, baos);
        byte[] bytes = baos.toByteArray();
        baos.flush();
        return bytes;
    }

    BufferedImage toBufferedImage(byte[] bytes) throws IOException {
        InputStream is = new ByteArrayInputStream(bytes);
        BufferedImage bi = ImageIO.read(is);
        is.close();
        return bi;
    }
}
