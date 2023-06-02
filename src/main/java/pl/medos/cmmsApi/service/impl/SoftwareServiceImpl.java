package pl.medos.cmmsApi.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.exception.SoftwareNotFoundException;
import pl.medos.cmmsApi.model.Software;
import pl.medos.cmmsApi.repository.SoftwareRepository;
import pl.medos.cmmsApi.repository.entity.SoftwareEntity;
import pl.medos.cmmsApi.service.SoftwareService;
import pl.medos.cmmsApi.service.mapper.SoftwareMapper;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class SoftwareServiceImpl implements SoftwareService {

    private static final Logger LOGGER = Logger.getLogger(SoftwareServiceImpl.class.getName());

    private SoftwareRepository softwareRepository;
    private SoftwareMapper softwareMapper;

    public SoftwareServiceImpl(SoftwareRepository softwareRepository, SoftwareMapper softwareMapper) {
        this.softwareRepository = softwareRepository;
        this.softwareMapper = softwareMapper;
    }

    @Override
    public List<Software> listAllSoftware() {
        LOGGER.info("listAllSoftware()");
        List<SoftwareEntity> softwareEntities = softwareRepository.findAll();
        List<Software> softwares = softwareMapper.listEntitiesToModels(softwareEntities);
        LOGGER.info("listAllSoftware(...)");
        return softwares;
    }

    @Override
    public Software create(Software software) {
        LOGGER.info("createSoftware()");
        SoftwareEntity softwareEntity = softwareMapper.mapModelToEntity(software);
        SoftwareEntity savedSoftwareEntity = softwareRepository.save(softwareEntity);
        Software savedSoftwareModel = softwareMapper.mapEntityToModel(savedSoftwareEntity);
        LOGGER.info("createSoftware(...)");
        return savedSoftwareModel;
    }

    @Override
    public Software read(Long id) throws SoftwareNotFoundException {
        LOGGER.info("findSoftware()");
        Optional<SoftwareEntity> softwareEntityOptional = softwareRepository.findById(id);
        SoftwareEntity softwareEntity = softwareEntityOptional.orElseThrow(
                () -> new SoftwareNotFoundException("Brak software o podanym id " + id)
        );
        Software software = softwareMapper.mapEntityToModel(softwareEntity);
        LOGGER.info("findSoftware(...)");
        return software;
    }

    @Override
    public Software update(Software software, Long id) throws SoftwareNotFoundException {
        LOGGER.info("updateSoftware()");
        Optional<SoftwareEntity> optionalSoftwareEntity = softwareRepository.findById(id);
        SoftwareEntity softwareEntityById = optionalSoftwareEntity.orElseThrow(
                () -> new SoftwareNotFoundException("Brak software'u o podanym Id " + id)
        );
        SoftwareEntity softwareEntity = softwareMapper.mapModelToEntity(software);
        softwareEntity.setId(softwareEntityById.getId());
        SoftwareEntity savedSoftwareEntity = softwareRepository.save(softwareEntity);
        Software savedSoftware = softwareMapper.mapEntityToModel(savedSoftwareEntity);
        LOGGER.info("updateSoftware(...)");
        return savedSoftware;
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("deleteSoftware(...)");
        softwareRepository.deleteById(id);
        LOGGER.info("deleteSoftware(...)");
    }

    @Override
    public Page pageSoftware(int pageNo, int size, String sortField, String sortDir) {
        LOGGER.info("pageSoftware()");
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, size, sort);
        Page<SoftwareEntity> softwareEntityPages = softwareRepository.findAll(pageable);
        Page<Software> softwares = softwareMapper.entitiesToModelsPage(softwareEntityPages);
        LOGGER.info("pageSoftware(...)");
        return softwares;
    }

    @Override
    public List<Software> findHardwaresByQuery(String query) {

        LOGGER.info("findSoftwareByQuery()");
        List<SoftwareEntity> softwareEntities = softwareRepository.searchSoftwareByQuery(query);
        List<Software> softwares = softwareMapper.listEntitiesToModels(softwareEntities);
        LOGGER.info("findSoftwareByQuery(...)");
        return softwares;

    }
}
