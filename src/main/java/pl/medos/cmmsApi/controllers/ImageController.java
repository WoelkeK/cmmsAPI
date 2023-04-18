package pl.medos.cmmsApi.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.exception.ImageNotFoundException;
import pl.medos.cmmsApi.model.Image;
import pl.medos.cmmsApi.service.ImageService;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/images")
public class ImageController {
    private ImageService service;

    public ImageController(ImageService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public String home(Model model) {
        List<Image> list = service.findAllImage();
        model.addAttribute("list", list);
        return "image-view";
    }

    @PostMapping("/upload")
    public String fileUpload(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        Image image = new Image();
        String fileName = file.getOriginalFilename();
        image.setProfilePicture(fileName);
        image.setContent(file.getBytes());
        image.setSize(file.getSize());
        service.createImage(image);
        model.addAttribute("success", "Plik załadowany prawidłowo!!");
        return "image-view";
    }


    @GetMapping("/downloadfile")
    public void downloadFile(@Param("id") Long id, Model model, HttpServletResponse response) throws ImageNotFoundException, IOException {
        Image image = service.findImageById(id);
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename = " + image.getProfilePicture();
        response.setHeader(headerKey, headerValue);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(image.getContent());
        outputStream.close();
    }

    @GetMapping("/image")
    public void showImage(@Param("id") Long id, HttpServletResponse response, Image image)
            throws ServletException, IOException, ImageNotFoundException {

        Image imageById = service.findImageById(id);
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif, image/pdf");
        response.getOutputStream().write(imageById.getContent());
        response.getOutputStream().close();
    }
}
