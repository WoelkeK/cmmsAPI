package pl.medos.cmmsApi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.exception.HardwareNotFoundException;
import pl.medos.cmmsApi.model.Hardware;
import pl.medos.cmmsApi.repository.HardwareRepository;
import pl.medos.cmmsApi.repository.entity.HardwareEntity;
import pl.medos.cmmsApi.service.HardwareService;
import pl.medos.cmmsApi.service.mapper.HardwareMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class HardwareServiceImpl implements HardwareService {

    private static final Logger LOGGER = Logger.getLogger(HardwareServiceImpl.class.getName());

    private final HardwareRepository hardwareRepository;
    private final HardwareMapper hardwareMapper;

    public List<Hardware> listAll() {
        LOGGER.info("listAll()");
        List<HardwareEntity> hardwareEntities = hardwareRepository.findAll();
        List<Hardware> hardwares = hardwareMapper.litsEntityToModels(hardwareEntities);
        LOGGER.info("listAll(...)");
        return hardwares;
    }

    public Page<Hardware> pagesHardware(int pageNo, int size, String sortField, String sortDirection) {
        LOGGER.info("pagesHardware()");
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, size, sort);
        Page<HardwareEntity> hardwareEntities = hardwareRepository.findAll(pageable);
        Page<Hardware> hardwares = hardwareMapper.pageEntityToModels(hardwareEntities);
        LOGGER.info("pagesHardware(...)");
        return hardwares;
    }

    @Override
    public List<Hardware> findAllSorted(String direction, String field) {
        LOGGER.info("findAllSorted()");
        Sort sort = direction.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(field).ascending() : Sort.by(field).descending();
        List<HardwareEntity> hardwareEntities = hardwareRepository.findAll(sort);
        List<Hardware> hardwares = hardwareMapper.litsEntityToModels(hardwareEntities);
        LOGGER.info("findAllSorted(...)");
        return hardwares;
    }

    @Override
    public Hardware create(Hardware hardware) {
        LOGGER.info("createHardware() " + hardware.getId());
        String inventoryNo = hardware.getInventoryNo().toUpperCase();
        hardware.setInventoryNo(inventoryNo);
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

    @Override
    public void deleteAll() {
        LOGGER.info("deleteAllHardware()");
        hardwareRepository.deleteAll();
        LOGGER.info("deleteAllHardware(...)");
    }

    @Override
    public List<Hardware> findByIpAddress(String ipAddres) {
        LOGGER.info("findByIpAddress");
        Optional<List<HardwareEntity>> byIpAddress = hardwareRepository.findByIpAddress(ipAddres);
        if (byIpAddress.isPresent()) {
            List<HardwareEntity> hardwareEntities = byIpAddress.get();
            return hardwareMapper.litsEntityToModels(hardwareEntities);
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public Page<Hardware> findHardwarePageByQuery(int pageNo, int pageSize, String sortField, String sortDir, String query) {
        LOGGER.info("findHardwarePageByQuery()");
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        Page<HardwareEntity> hardwareEntityPage = hardwareRepository.findByQueryPagable(query, pageable);
        Page<Hardware> hardware = hardwareMapper.pageEntityToModels(hardwareEntityPage);
        LOGGER.info("findHardwarePageByQuery(...)");
        return hardware;
    }
}
