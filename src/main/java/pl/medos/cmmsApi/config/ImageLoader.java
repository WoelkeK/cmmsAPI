package pl.medos.cmmsApi.config;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class ImageLoader {

    public ImageLoader() {
        loadFileFromClasspath();
    }

    private void loadFileFromClasspath() {
        String relativePath = "static/images/default.jpg"; // Relative path within resources

        try {
            ClassPathResource resource = new ClassPathResource(relativePath);
            Path filePath = resource.getFile().toPath();

            // Now you can use 'filePath' to read or manipulate the file
            // For example:
             Files.copy(filePath, Paths.get("/home/images/default.jpg"));
            // (Uncomment the above line to copy the file to /home/images)

            System.out.println("File loaded successfully: " + filePath);
        } catch (IOException e) {
            System.err.println("Error loading file: " + e.getMessage());
        }
    }
}
