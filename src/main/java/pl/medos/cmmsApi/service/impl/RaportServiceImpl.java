package pl.medos.cmmsApi.service.impl;

import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.model.Hardware;
import pl.medos.cmmsApi.service.RaportService;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class RaportServiceImpl implements RaportService {

    private static final Logger LOGGER = Logger.getLogger(RaportServiceImpl.class.getName());
    private String filePath = "src/main/resources/templates/test.jrxml";
    private String content = "stanowiącego własność firmy MEDOS. Sprzęt w/w zobowiązuję się zwrócić na każde żądanie właściciela w takim samym stanie, " +
            "jakim został mi przekazany w dniu wypożyczenia uwzględniając mechaniczne uszkodzenia wynikające z naturalnego eksploatowania sprzętu.";

    @Override
    public void generateReport(Hardware hardware) throws JRException {

        LOGGER.info("generateReport()");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("protocolType", "Wypożyczenia");
        parameters.put("protocolTypeShort", "wypożyczenie");
        parameters.put("employeeName", hardware.getEmployee().getName());
        parameters.put("hardwareName", hardware.getName());
        parameters.put("hardwareType", hardware.getType().toString());
        parameters.put("inventoryNo", hardware.getInventoryNo());
        parameters.put("content", content);
        parameters.put("serialNo", hardware.getSerialNumber());

        JasperReport report = JasperCompileManager.compileReport(filePath);

        JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
        JasperExportManager.exportReportToPdfFile(print, "C:/XL/report.pdf");
        LOGGER.info("Report Generated successfully");

    }
}
