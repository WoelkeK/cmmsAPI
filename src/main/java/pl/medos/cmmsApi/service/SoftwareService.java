package pl.medos.cmmsApi.service;

import org.springframework.data.domain.Page;
import pl.medos.cmmsApi.exception.SoftwareNotFoundException;
import pl.medos.cmmsApi.model.Software;

import java.util.List;

public interface SoftwareService {


   List<Software> listAllSoftware();

    public Software create(Software software);
    public Software read(Long id) throws SoftwareNotFoundException;
    public Software update(Software software, Long id) throws SoftwareNotFoundException;
    public void delete(Long id);

    Page pageSoftware(int pageNo, int size, String sortField, String sortDir);

    List<Software> findHardwaresByQuery(String query);

}

