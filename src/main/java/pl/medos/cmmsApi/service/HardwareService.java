package pl.medos.cmmsApi.service;

import pl.medos.cmmsApi.model.Hardware;

public interface HardwareService {

    void create(Hardware hardware);
    void read(Long id);
    void update(Hardware hardware);
    void delete(Long id);

}
