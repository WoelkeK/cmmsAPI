package pl.medos.cmmsApi.service.impl;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
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
        return bytesImage;
    }

    @Override
    public byte[] imageToByteArray() throws IOException {
        LOGGER.info("imageToByteArray()");
        URL resource = getClass().getResource("/image.jpg");
        BufferedImage bf = ImageIO.read(resource);
        byte[] defaultImage = toByteArray(bf, "jpg");
        return defaultImage;
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
        return bi;
    }
}
