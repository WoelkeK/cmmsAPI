package pl.medos.cmmsApi.util.imports;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.enums.Permission;
import pl.medos.cmmsApi.model.Hardware;
import pl.medos.cmmsApi.model.JsonHardware;
import pl.medos.cmmsApi.service.HardwareService;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;

@Component
public class ImportHardwareFromXls implements ImportHardware {

    private static final Logger LOGGER = Logger.getLogger(ImportHardwareFromXls.class.getName());
    @Autowired
    private HardwareService hardwareService;

    private List<String> hardwares = new ArrayList<>(Arrays.asList(
            "inventoryNo", "department", "status", "employee", "type", "name", "installDate", "invoiceNo", "systemNo",
            "serialNumber", "netBios", "ipAddress", "macAddress", "officeName", "officeNo", "activateDate", "description",
            "bitLockKey", "bitRecoveryKey", "permission", "nRead", "nEdit", "nDelete", "eRead", "eEdit", "eDelete", "pRead", "pEdit",
            "pDelete", "dRead", "dEdit", "dDelete", "mRead", "mEdit", "mDelete", "jRead", "jEdit", "jDelete"));

    @Override
    public List<Hardware> importHardware(MultipartFile fileName) throws IOException {

        LOGGER.info("importExcelHardwareData()");
        List<JsonHardware> rawDataList = new ArrayList<>();
        InputStream file = new BufferedInputStream(fileName.getInputStream());

        IOUtils.setByteArrayMaxOverride(Integer.MAX_VALUE);
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            String empty = "";

            if (row.getRowNum() == 0) {
                continue;
            }

            Map<String, String> rowDataMap = new HashMap<>();
            Cell cell;
            for (int k = 0; k < row.getLastCellNum(); k++) {
                if (null != (cell = row.getCell(k))) {
                    switch (cell.getCellType()) {
                        case NUMERIC:
//                            rowDataMap.put(hardwares.get(k), NumberToTextConverter.toText(cell.getNumericCellValue()));
                            rowDataMap.put(hardwares.get(k), (cell.getDateCellValue().toString()));
                            break;
                        case STRING:
//                            rowDataMap.put(persons.get(k), cell.getStringCellValue());
                            rowDataMap.put(hardwares.get(k), cell.getStringCellValue().replaceAll("  ", " ").trim());
                            break;
                        case BOOLEAN:
                            rowDataMap.put(hardwares.get(k), String.valueOf(cell.getBooleanCellValue()));
                        case _NONE:
                            rowDataMap.put(hardwares.get(k), empty);
                            break;

                    }
                }
            }

            ObjectMapper mapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

            JsonHardware rawData = mapper.convertValue(rowDataMap, JsonHardware.class);
            rawDataList.add(rawData);
            LOGGER.info("rawData " + rawData);
        }
        List<Hardware> hardwares = hardwareDataExcelConverter(rawDataList);
        return hardwares;
    }

    private List<Hardware> hardwareDataExcelConverter(List<JsonHardware> hardwares) {
        LOGGER.info("hardwareDataExcelConverter()");

        List<Hardware> convertedHardwares =
                hardwares.stream().map(m -> {
                                    LOGGER.info("Row create()");
                                    Hardware hardware = new Hardware();
                                    hardware.setInventoryNo(m.getInventoryNo());
                                    hardware.setDepartment(m.getDepartment());
                                    hardware.setStatus(m.getStatus());
                                    hardware.setEmployee(m.getEmployee());
                                    hardware.setType(m.getType());
                                    hardware.setName(m.getName());
                                    hardware.setInvoiceNo(m.getInvoiceNo());
                                    hardware.setSystemNo(m.getSystemNo());
                                    hardware.setSerialNumber(m.getSerialNumber());
                                    hardware.setNetBios(m.getNetBios());
                                    hardware.setMacAddress(m.getMacAddress());
                                    hardware.setOfficeName(m.getOfficeName());
                                    hardware.setOfficeNo(m.getOfficeNo());
                                    hardware.setBitLockKey(m.getBitLockKey());
                                    hardware.setBitRecoveryKey(m.getBitRecoveryKey());
                                    hardware.setDescription(m.getDescription());

                                    Hardware dbHardware = hardwareService.findByIpAddress(m.getIpAddress());
                                    if(dbHardware.getIpAddress()==null) {
                                        hardware.setIpAddress(m.getIpAddress());
                                    }

                                    if (m.getPermission() == null) {
                                        hardware.setPermission(Permission.USER);
                                    } else {

                                        hardware.setPermission(m.getPermission());
                                    }

                                    if (m.getInstallDate() == null || m.getInstallDate().isEmpty()) {
                                        hardware.setInstallDate(null);
                                    } else {
                                        LocalDate installDate = DateConverter.convertDate(m.getInstallDate());
                                        hardware.setInstallDate(installDate);
                                        LOGGER.info(installDate.toString());
                                    }
                                    if (m.getActivateDate() == null || m.getActivateDate().isEmpty()) {
                                        hardware.setActivateDate(null);
                                    } else {
                                        LocalDate activateDate = DateConverter.convertDate(m.getActivateDate());
                                        hardware.setActivateDate(activateDate);
                                    }
                                    hardware.setNRead(convertXLSField(m.getNRead()));
                                    hardware.setNEdit(convertXLSField(m.getNEdit()));
                                    hardware.setNDelete(convertXLSField(m.getNDelete()));

                                    hardware.setERead(convertXLSField(m.getERead()));
                                    hardware.setEEdit(convertXLSField(m.getEEdit()));
                                    hardware.setEDelete(convertXLSField(m.getEDelete()));

                                    hardware.setDRead(convertXLSField(m.getPRead()));
                                    hardware.setDEdit(convertXLSField(m.getPEdit()));
                                    hardware.setDDelete(convertXLSField(m.getPDelete()));

                                    hardware.setPRead(convertXLSField(m.getDRead()));
                                    hardware.setPEdit(convertXLSField(m.getDEdit()));
                                    hardware.setPDelete(convertXLSField(m.getDDelete()));

                                    hardware.setMRead(convertXLSField(m.getMRead()));
                                    hardware.setMEdit(convertXLSField(m.getMEdit()));
                                    hardware.setMDelete(convertXLSField(m.getMDelete()));

                                    hardware.setJRead(convertXLSField(m.getJRead()));
                                    hardware.setJEdit(convertXLSField(m.getJRead()));
                                    hardware.setJDelete(convertXLSField(m.getJRead()));

                                    LOGGER.info("Row create(...)");
                                    return hardware;
                                }
                        )
                        .toList();

        LOGGER.info("hardwareDataExcelConverter(...)");
        return convertedHardwares;
    }

    static Boolean convertXLSField(String condition) {

        if (condition != null) {

            if (condition.equalsIgnoreCase("fałsz")) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
