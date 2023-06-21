package pl.medos.cmmsApi.service.impl;

import net.sf.jasperreports.engine.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.model.Hardware;
import pl.medos.cmmsApi.service.RaportService;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class RaportServiceImpl implements RaportService {

    private static final Logger LOGGER = Logger.getLogger(RaportServiceImpl.class.getName());
    private String filePath = "src/main/resources/templates/test.jrxml";
    private String content = "stanowiącego własność firmy MEDOS. Sprzęt w/w zobowiązuję się zwrócić na każde żądanie właściciela w takim samym stanie, " +
            "jakim został mi przekazany w dniu wypożyczenia uwzględniając mechaniczne uszkodzenia wynikające z naturalnego eksploatowania sprzętu.";

    public JasperPrint getJasperPrint(Hardware hardware, JasperReport jasperReport) throws FileNotFoundException, JRException {

        LOGGER.info("getJasperPrint()");

        Map<String, Object> parameters = new HashMap<>();
;
        parameters.put("employeeName", hardware.getEmployee());
        parameters.put("hardwareName", hardware.getName());
        parameters.put("hardwareType", hardware.getType().toString());
        parameters.put("inventoryNo", hardware.getInventoryNo());
        parameters.put("content", content);
        parameters.put("serialNo", hardware.getSerialNumber());

        switch (hardware.getDocument().toString()) {
            case "ZDANIE":
                parameters.put("protocolType", "Zdania");
                parameters.put("protocolTypeShort", "zdanie");
                break;
            default:
                parameters.put("protocolType", "Wypożyczenia");
                parameters.put("protocolTypeShort", "wypożyczenie");
        }

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        return jasperPrint;
    }

    @Override
    public void exportReport(Hardware hardware, OutputStream outputStream) throws JRException, FileNotFoundException {

        Resource resource = new ClassPathResource("reports/test.jrxml");

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource.getInputStream());
            JasperPrint jasperPrint = getJasperPrint(hardware, jasperReport);
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
