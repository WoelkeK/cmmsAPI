package pl.medos.cmmsApi.service;


import pl.medos.cmmsApi.exception.ResourceNotFoundException;
import pl.medos.cmmsApi.model.Resource;

import java.util.List;

public interface ResourceService {

    List<Resource> findAllResources();

    Resource createResource(Resource resource);

    Resource findResourceById(Long id) throws ResourceNotFoundException;

    Resource updateResource(Resource resource);

    void deleteResource(Long id);
}
