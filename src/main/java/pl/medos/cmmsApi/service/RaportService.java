package pl.medos.cmmsApi.service;

import net.sf.jasperreports.engine.JRException;
import pl.medos.cmmsApi.model.Hardware;
import pl.medos.cmmsApi.model.Notification;

import java.io.FileNotFoundException;
import java.io.OutputStream;

public interface RaportService {
    void exportReport(Hardware hardware, OutputStream outputStream) throws JRException, FileNotFoundException;
    void exportReport(Notification notification, OutputStream outputStream) throws JRException, FileNotFoundException;
}