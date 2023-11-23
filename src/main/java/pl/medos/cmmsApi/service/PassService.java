package pl.medos.cmmsApi.service;

import org.springframework.data.domain.Page;
import pl.medos.cmmsApi.model.Pass;

import java.util.List;

public interface PassService {

    Page<Pass> findPagePasses(int pageNo, int size, String sortField, String sortDirection);

    public Pass findPassById(Long id);
    public Pass createPass(Pass pass);
    public Pass updatePass(Pass pass, Long id);
    public void deletePass(Long id);


    List<Pass> findPassByQuery(String query);
}
