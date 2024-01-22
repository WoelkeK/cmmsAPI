package pl.medos.cmmsApi.service;

import org.springframework.data.domain.Page;
import pl.medos.cmmsApi.exception.HardwareNotFoundException;
import pl.medos.cmmsApi.model.Hardware;
import pl.medos.cmmsApi.model.Notification;
import pl.medos.cmmsApi.repository.entity.HardwareEntity;

import java.util.List;

public interface HardwareService {

    Hardware create(Hardware hardware);
    Hardware read(Long id) throws HardwareNotFoundException;
    Hardware update(Hardware hardware) throws HardwareNotFoundException;
    void delete(Long id);
    List<Hardware> listAll();
    Page<Hardware> pagesHardware(int pageNo, int size, String sortField, String sortDirection);

    List<Hardware> findHardwaresByQuery(String query);

    List<Hardware> findAllSorted(String direction, String field);

    void deleteAll();

//    Boolean findHardwareByIpAddress(String clientIp);

//    Boolean findHardwareByIpAddress(String clientIp);

    Hardware findByIpAddress(String remoteIP);

    Page<Hardware> findHardwarePageByQuery(int pageNo, int pageSize, String sortField, String sortDir, String query);
}

