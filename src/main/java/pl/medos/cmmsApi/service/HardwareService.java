package pl.medos.cmmsApi.service;

import org.springframework.data.domain.Page;
import pl.medos.cmmsApi.exception.HardwareNotFoundException;
import pl.medos.cmmsApi.model.Hardware;

import java.util.List;

public interface HardwareService {

    Hardware create(Hardware hardware);
    Hardware read(Long id) throws HardwareNotFoundException;
    Hardware update(Hardware hardware) throws HardwareNotFoundException;
    void delete(Long id);
    List<Hardware> listAll();
    Page<Hardware> pagesHardware(int pageNo, int size, String sortField, String sortDirection);

    List<Hardware> findHardwaresByQuery(String query);

    void deleteAll();
}

