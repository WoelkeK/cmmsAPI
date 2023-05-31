package pl.medos.cmmsApi.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.exception.HardwareNotFoundException;
import pl.medos.cmmsApi.model.Hardware;
import pl.medos.cmmsApi.model.Machine;
import pl.medos.cmmsApi.repository.HardwareRepository;
import pl.medos.cmmsApi.repository.entity.HardwareEntity;
import pl.medos.cmmsApi.repository.entity.MachineEntity;
import pl.medos.cmmsApi.service.HardwareService;
import pl.medos.cmmsApi.service.mapper.HardwareMapper;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class HardwareServiceImpl implements HardwareService {

    private static final Logger LOGGER = Logger.getLogger(HardwareServiceImpl.class.getName());

    private HardwareRepository hardwareRepository;
    private HardwareMapper hardwareMapper;

    public HardwareServiceImpl(HardwareRepository hardwareRepository, HardwareMapper hardwareMapper) {
        this.hardwareRepository = hardwareRepository;
        this.hardwareMapper = hardwareMapper;
    }

    public List<Hardware> listAll() {
        LOGGER.info("listAll()");
        List<HardwareEntity> hardwareEntities = hardwareRepository.findAll();
        List<Hardware> hardwares = hardwareMapper.litsEntityToModels(hardwareEntities);
        LOGGER.info("listAll(...)");
        return hardwares;
    }

    public Page<Hardware> pagesHardware(int pageNo, int size) {
        LOGGER.info("pagesHardware()");
        Pageable pageable = PageRequest.of(pageNo, size);
        Page<HardwareEntity> hardwareEntities = hardwareRepository.findAll(pageable);
        Page<Hardware> hardwares = hardwareMapper.pageEntityToModels(hardwareEntities);
        LOGGER.info("pagesHardware(...)");
        return hardwares;
    }

    @Override
    public List<Hardware> findHardwaresByQuery(String query) {
        LOGGER.info("findHardwaresByQuery()" + query);
        List<HardwareEntity> hardwareEntities = hardwareRepository.searchHardwareByQuery(query);
        List<Hardware> hardwares = hardwareMapper.litsEntityToModels(hardwareEntities);
        LOGGER.info("findHardwaresByQuery(...)");
        return hardwares;
    }

    @Override
    public Hardware create(Hardware hardware) {
        LOGGER.info("createHardware() " + hardware.getId());
        HardwareEntity hardwareEntity = hardwareMapper.mapModelToEntity(hardware);
        HardwareEntity saveHardwareEntity = hardwareRepository.save(hardwareEntity);
        Hardware savedHardware = hardwareMapper.mapEntityToModel(saveHardwareEntity);
        LOGGER.info("createHardware(...)" + savedHardware);
        return savedHardware;
    }

    @Override
    public Hardware read(Long id) throws HardwareNotFoundException {
        LOGGER.info("readHardware(" + id + ")");
        Optional<HardwareEntity> optionalHardwareEntity = hardwareRepository.findById(id);
        HardwareEntity hardwareEntity = optionalHardwareEntity.orElseThrow(
                () -> new HardwareNotFoundException("Nie odnaleziono sprzętu o podanym id.")
        );
        Hardware hardware = hardwareMapper.mapEntityToModel(hardwareEntity);
        LOGGER.info("readHardware(...) " + hardware);
        return hardware;
    }

    @Override
    public Hardware update(Hardware hardware) throws HardwareNotFoundException {
        LOGGER.info("mapUpdateHardware()" + hardware.getId());
        HardwareEntity hardwareEntity = hardwareMapper.mapModelToEntity(hardware);
        hardwareEntity.setId(hardware.getId());
        HardwareEntity saveHardwareEntity = hardwareRepository.save(hardwareEntity);
        Hardware savedHardware = hardwareMapper.mapEntityToModel(saveHardwareEntity);
        LOGGER.info("mapUpdateHardware(...)" + savedHardware);
        return savedHardware;
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("deleteHardware(" + id + ")");
        hardwareRepository.deleteById(id);
        LOGGER.info("deleteHardware(...)");

    }
}
