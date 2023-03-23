package pl.medos.cmmsApi.service;

import pl.medos.cmmsApi.model.Part;

import java.util.List;

public interface PartService {

    List<Part> findAll();

    void saveAll(List<Part> parts);


}
