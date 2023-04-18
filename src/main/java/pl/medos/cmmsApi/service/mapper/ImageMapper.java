package pl.medos.cmmsApi.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.medos.cmmsApi.model.Image;
import pl.medos.cmmsApi.repository.ImageRepository;
import pl.medos.cmmsApi.repository.entity.ImageEntity;

import java.util.List;
import java.util.logging.Logger;

@Component
public class ImageMapper {
    private static final Logger LOGGER = Logger.getLogger(ImageMapper.class.getName());
    private final ImageRepository imageRepository;

    public ImageMapper(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<Image> listImageModels(List<ImageEntity> imageEntities) {
        LOGGER.info("listImageModels()");
        List<Image> images = imageEntities.stream()
                .map(this::entityToModel)
                .toList();
        LOGGER.info("listImageModels(...)");
        return images;
    }


    public ImageEntity modelToEntity(Image image) {
        LOGGER.info("modelToEntity()");
        ModelMapper modelMapper = new ModelMapper();
        ImageEntity imageEntity = modelMapper.map(image, ImageEntity.class);
        LOGGER.info("modelToEntity(...)");
        return imageEntity;
    }

    public Image entityToModel(ImageEntity imageEntity) {
        LOGGER.info("modelToEntity()");
        ModelMapper modelMapper = new ModelMapper();
        Image image = modelMapper.map(imageEntity, Image.class);
        LOGGER.info("modelToEntity(...)");
        return image;
    }
}
