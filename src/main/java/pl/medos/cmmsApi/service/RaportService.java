package pl.medos.cmmsApi.service;

import net.sf.jasperreports.engine.JRException;
import pl.medos.cmmsApi.model.Hardware;

public interface RaportService {

    void generateReport(Hardware hardware)throws JRException;
}
