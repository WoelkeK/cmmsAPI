package pl.medos.cmmsApi.service.impl;

import net.sf.jasperreports.engine.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.model.Hardware;
import pl.medos.cmmsApi.model.Notification;
import pl.medos.cmmsApi.service.RaportService;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class RaportServiceImpl implements RaportService {

    private static final Logger LOGGER = Logger.getLogger(RaportServiceImpl.class.getName());
    private String filePath1 = "src/main/resources/templates/Pzo.jrxml";
    private String filePath2 = "src/main/resources/templates/awizo.jrxml";
    private String content = "stanowiącego własność firmy MEDOS. Sprzęt w/w zobowiązuję się zwrócić na każde żądanie właściciela w takim samym stanie, " +
            "jakim został mi przekazany w dniu wypożyczenia uwzględniając mechaniczne uszkodzenia wynikające z naturalnego eksploatowania sprzętu.";

    public JasperPrint getJobJasperPrint(Hardware hardware, JasperReport jasperReport) throws FileNotFoundException, JRException {

        LOGGER.info("getJasperPrint()");

        Map<String, Object> parameters = new HashMap<>();
        ;
        parameters.put("employeeName", hardware.getEmployee());
        parameters.put("hardwareName", hardware.getName());
        parameters.put("hardwareType", hardware.getType().toString());
        parameters.put("inventoryNo", hardware.getInventoryNo());

        parameters.put("serialNo", hardware.getSerialNumber());
        parameters.put("comment", hardware.getDescription());

        switch (hardware.getDocument().toString()) {
            case "ZDANIE":
                parameters.put("protocolType", "Zdania");
                parameters.put("protocolTypeShort", "zdanie");
                parameters.put("content", "stanowiącego własność firmy MEDOS.");
                parameters.put("employeeSign", "podpis osoby zdającej");
                parameters.put("managerSign", "podpis osoby przyjmującej");
                break;
            default:
                parameters.put("protocolType", "Wypożyczenia");
                parameters.put("protocolTypeShort", "wypożyczenie");
                parameters.put("content", content);
                parameters.put("employeeSign", "podpis osoby przyjmującej");
                parameters.put("managerSign", "podpis osoby wypożyczającej");
        }

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        return jasperPrint;
    }

    public JasperPrint getJasperPrint(Notification notification, JasperReport jasperReport) throws FileNotFoundException, JRException {

        LOGGER.info("getJasperPrint()");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("employee", notification.getEmployee());
        parameters.put("employeePhone", "+48 666 777 888");
        parameters.put("driverName", notification.getDriverName());
        parameters.put("item", notification.getItem());
        parameters.put("description", notification.getDescription());
        parameters.put("status", notification.getStatus().toString());
        parameters.put("carPlates", notification.getCarPlates());
        parameters.put("type", notification.getType().toString());
        parameters.put("comment", notification.getComment());
        parameters.put("supplier", notification.getSupplier());
        parameters.put("driverPhone", notification.getDriverPhone());
        parameters.put("managerSign", "podpis portiera");
        parameters.put("employeeSign", "podpis kierowcy");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        return jasperPrint;
    }

    @Override
    public void exportReport(Hardware hardware, OutputStream outputStream) throws JRException, FileNotFoundException {

        Resource resource = new ClassPathResource("reports/Pzo.jrxml");

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource.getInputStream());
            JasperPrint jasperPrint = getJobJasperPrint(hardware, jasperReport);
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exportReport(Notification notification, OutputStream outputStream) throws JRException, FileNotFoundException {

        Resource resource = new ClassPathResource("reports/awizo.jrxml");

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource.getInputStream());
            JasperPrint jasperPrint = getJasperPrint(notification, jasperReport);
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
