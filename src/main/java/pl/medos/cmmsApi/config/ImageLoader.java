package pl.medos.cmmsApi.config;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
@Component
public class ImageLoader {

    private static final String UPLOAD_DIR = System.getProperty("user.home") + File.separator+"images";
    public ImageLoader() {
        loadFileFromClasspath();
    }

    private void loadFileFromClasspath() {
        String relativePath = "static/images/default.jpg"; // Relative path within resources
        Path destinationPathObj = null;

        try {
            System.out.println("Home dir: " + UPLOAD_DIR);
            ClassPathResource resource = new ClassPathResource(relativePath);
            InputStream inputStream = resource.getInputStream();
            String destinationPath = System.getProperty("user.home") + File.separator + "images" + File.separator + "default.jpg";
            destinationPathObj = Paths.get(destinationPath);
            Files.createDirectories(destinationPathObj.getParent());
            Files.copy(inputStream, destinationPathObj, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        if (destinationPathObj != null) {
            System.out.println("File uploaded successfully: " + destinationPathObj.toString());
        } else {
            System.err.println("Error: destinationPathObj is null");
        }
    }
}