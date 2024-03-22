package pl.medos.cmmsApi.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class HardwareServiceImpl implements HardwareService {

    private final HardwareRepository hardwareRepository;
    private final HardwareMapper hardwareMapper;

    public HardwareServiceImpl(HardwareRepository hardwareRepository, HardwareMapper hardwareMapper) {
        this.hardwareRepository = hardwareRepository;
        this.hardwareMapper = hardwareMapper;
    }

    public List<Hardware> listAll() {
        log.debug("listAll()");
        List<HardwareEntity> hardwareEntities = hardwareRepository.findAll();
        List<Hardware> hardwares = hardwareMapper.litsEntityToModels(hardwareEntities);
        log.debug("listAll(...)");
        return hardwares;
    }

    public Page<Hardware> pagesHardware(int pageNo, int size, String sortField, String sortDirection) {
        log.debug("pagesHardware()");
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, size, sort);
        Page<HardwareEntity> hardwareEntities = hardwareRepository.findAll(pageable);
        Page<Hardware> hardwares = hardwareMapper.pageEntityToModels(hardwareEntities);
        log.debug("pagesHardware(...)");
        return hardwares;
    }

    @Override
    public List<Hardware> findAllSorted(String direction, String field) {
        log.debug("findAllSorted()");
        Sort sort = direction.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(field).ascending() : Sort.by(field).descending();
        List<HardwareEntity> hardwareEntities = hardwareRepository.findAll(sort);
        List<Hardware> hardwares = hardwareMapper.litsEntityToModels(hardwareEntities);
        log.debug("findAllSorted(...)");
        return hardwares;
    }

    @Override
    public Hardware create(Hardware hardware) {
        log.debug("createHardware() " + hardware.getId());
        String inventoryNo = hardware.getInventoryNo().toUpperCase();
        hardware.setInventoryNo(inventoryNo);
        HardwareEntity hardwareEntity = hardwareMapper.mapModelToEntity(hardware);
        HardwareEntity saveHardwareEntity = hardwareRepository.save(hardwareEntity);
        Hardware savedHardware = hardwareMapper.mapEntityToModel(saveHardwareEntity);
        log.debug("createHardware(...)" + savedHardware);
        return savedHardware;
    }

    @Override
    public Hardware read(Long id) throws HardwareNotFoundException {
        log.debug("readHardware(" + id + ")");
        Optional<HardwareEntity> optionalHardwareEntity = hardwareRepository.findById(id);
        HardwareEntity hardwareEntity = optionalHardwareEntity.orElseThrow(
                () -> new HardwareNotFoundException("Nie odnaleziono sprzętu o podanym id.")
        );
        Hardware hardware = hardwareMapper.mapEntityToModel(hardwareEntity);
        log.debug("readHardware(...) " + hardware);
        return hardware;
    }

    @Override
    public Hardware update(Hardware hardware) throws HardwareNotFoundException {
        log.debug("mapUpdateHardware()" + hardware.getId());
        HardwareEntity hardwareEntity = hardwareMapper.mapModelToEntity(hardware);
        hardwareEntity.setId(hardware.getId());
        HardwareEntity saveHardwareEntity = hardwareRepository.save(hardwareEntity);
        Hardware savedHardware = hardwareMapper.mapEntityToModel(saveHardwareEntity);
        log.debug("mapUpdateHardware(...)" + savedHardware);
        return savedHardware;
    }

    @Override
    public void delete(Long id) {
        log.debug("deleteHardware(" + id + ")");
        hardwareRepository.deleteById(id);
        log.debug("deleteHardware(...)");
    }

    @Override
    public void deleteAll() {
        log.debug("deleteAllHardware()");
        hardwareRepository.deleteAll();
        log.debug("deleteAllHardware(...)");
    }

    @Override
    public List<Hardware> findByIpAddress(String ipAddres) {
        log.debug("findByIpAddress");
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
        log.debug("findHardwarePageByQuery()");
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        Page<HardwareEntity> hardwareEntityPage = hardwareRepository.findByQueryPagable(query, pageable);
        Page<Hardware> hardware = hardwareMapper.pageEntityToModels(hardwareEntityPage);
        log.debug("findHardwarePageByQuery(...)");
        return hardware;
    }
}
