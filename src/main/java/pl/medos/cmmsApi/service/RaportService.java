package pl.medos.cmmsApi.service;

import net.sf.jasperreports.engine.JRException;
import pl.medos.cmmsApi.model.Hardware;
import java.io.FileNotFoundException;
import java.io.OutputStream;

public interface RaportService {
    void exportReport(Hardware hardware, OutputStream outputStream) throws JRException, FileNotFoundException;
}