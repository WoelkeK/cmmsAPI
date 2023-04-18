package pl.medos.cmmsApi.service.impl;

import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.exception.ImageNotFoundException;
import pl.medos.cmmsApi.model.Image;
import pl.medos.cmmsApi.repository.ImageRepository;
import pl.medos.cmmsApi.repository.entity.ImageEntity;
import pl.medos.cmmsApi.service.ImageService;
import pl.medos.cmmsApi.service.mapper.ImageMapper;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ImageServiceImpl implements ImageService {

    private static final Logger LOGGER = Logger.getLogger(ImportServiceImpl.class.getName());

    private ImageRepository imageRepository;
    private ImageMapper imageMapper;

    public ImageServiceImpl(ImageRepository imageRepository, ImageMapper imageMapper) {
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
    }

    @Override
    public void createImage(Image image) {
        LOGGER.info("createImage()");
        ImageEntity imageEntity = imageMapper.modelToEntity(image);
        ImageEntity savedImageEntity = imageRepository.save(imageEntity);
        Image imageModel = imageMapper.entityToModel(savedImageEntity);
        LOGGER.info("createImage(...)");

    }

    @Override
    public List<Image> findAllImage() {
        LOGGER.info("findAllImage()");
        List<ImageEntity> imageEntities = imageRepository.findAll();
        List<Image> images = imageMapper.listImageModels(imageEntities);
        LOGGER.info("findAllImage(...)");
        return images;
    }

    @Override
    public Image findImageById(Long id) throws ImageNotFoundException {
        LOGGER.info("findImageById()");
        Optional<ImageEntity> optionalImageEntity = imageRepository.findById(id);
        ImageEntity imageEntity = optionalImageEntity.orElseThrow(
                () -> new ImageNotFoundException("Nie ma pliku obrazu o podanym id " + id)
        );
        Image image = imageMapper.entityToModel(imageEntity);
        LOGGER.info("findImageById(...)");
        return image;
    }
}
