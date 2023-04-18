package pl.medos.cmmsApi.service;

import pl.medos.cmmsApi.exception.ImageNotFoundException;
import pl.medos.cmmsApi.model.Image;

import java.util.List;

public interface ImageService {

    public void createImage(Image image);

    public List<Image> findAllImage();

    public Image findImageById(Long id) throws ImageNotFoundException;


}
