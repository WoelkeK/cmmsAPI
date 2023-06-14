package pl.medos.cmmsApi.service.impl;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import pl.medos.cmmsApi.model.Hardware;
import pl.medos.cmmsApi.service.RaportService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class RaportServiceImpl implements RaportService {

    private static final Logger LOGGER = Logger.getLogger(RaportServiceImpl.class.getName());
    private String filePath = "src/main/resources/templates/test.jrxml";
    private String content = "stanowiącego własność firmy MEDOS. Sprzęt w/w zobowiązuję się zwrócić na każde żądanie właściciela w takim samym stanie, " +
            "jakim został mi przekazany w dniu wypożyczenia uwzględniając mechaniczne uszkodzenia wynikające z naturalnego eksploatowania sprzętu.";

    public JasperPrint getJasperPrint(Hardware hardware, String resourceLocation) throws FileNotFoundException, JRException {
        File file = ResourceUtils.getFile(resourceLocation);
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        LOGGER.info("getJasperPrint()");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("protocolType", "Wypożyczenia");
        parameters.put("protocolTypeShort", "wypożyczenie");
        parameters.put("employeeName", hardware.getEmployee().getName());
        parameters.put("hardwareName", hardware.getName());
        parameters.put("hardwareType", hardware.getType().toString());
        parameters.put("inventoryNo", hardware.getInventoryNo());
        parameters.put("content", content);
        parameters.put("serialNo", hardware.getSerialNumber());

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        return jasperPrint;
    }

    @Override
    public void exportReport(Hardware hardware, OutputStream outputStream) throws JRException, FileNotFoundException {
        String resourceLocation = "src/main/resources/templates/test.jrxml";
        JasperPrint jasperPrint = getJasperPrint(hardware, resourceLocation);
        JasperExportManager.exportReportToPdfStream(jasperPrint,outputStream);
    }
}
