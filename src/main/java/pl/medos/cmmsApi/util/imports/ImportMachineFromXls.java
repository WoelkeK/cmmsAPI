package pl.medos.cmmsApi.util.imports;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Machine;
import pl.medos.cmmsApi.model.MachineDep;
import pl.medos.cmmsApi.model.Person;
import pl.medos.cmmsApi.service.DepartmentService;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;

@Component
public class ImportMachineFromXls implements ImportMachine{

    private static final Logger LOGGER = Logger.getLogger(ImportHardwareFromXls.class.getName());

    private List<String> machines = new ArrayList<>(Arrays.asList("name", "model", "manufactured", "serialNumber", "installDate", "status", "department"));

    private DepartmentService departmentService;

    public ImportMachineFromXls(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Override
    public List<Machine> importExcelMachineData(MultipartFile fileName) throws IOException {
        LOGGER.info("importExcelMAchinesData()");

        List<MachineDep> rawDataList = new ArrayList<>();
        InputStream file = new BufferedInputStream(fileName.getInputStream());
        IOUtils.setByteArrayMaxOverride(Integer.MAX_VALUE);
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Person person = new Person();
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            if (row.getRowNum() == 0) {
                continue;
            }

            Map<String, String> rowDataMap = new HashMap<>();
            Cell cell;
            for (int k = 0; k < row.getLastCellNum(); k++) {
                if (null != (cell = row.getCell(k))) {
                    switch (cell.getCellType()) {
                        case NUMERIC:
                            rowDataMap.put(machines.get(k), NumberToTextConverter.toText(cell.getNumericCellValue()));
                            break;
                        case STRING:
//                            rowDataMap.put(persons.get(k), cell.getStringCellValue());
                            rowDataMap.put(machines.get(k), cell.getStringCellValue().replaceAll("  ", " ").trim());
                            break;
                    }
                }
            }
            ObjectMapper mapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
            MachineDep rawData = mapper.convertValue(rowDataMap, MachineDep.class);
            rawDataList.add(rawData);
            LOGGER.info("rawData " + rawData);
        }
        List<Machine> machines = machineDataExcelConverter(rawDataList);
        return machines;
    }

    private List<Machine> machineDataExcelConverter(List<MachineDep> machineDeps) {
        LOGGER.info("machineDataExcelConverter()");

        List<Machine> convertedMachines =
                machineDeps.stream().map(m -> {

                                    Machine machine = new Machine();
                                    machine.setName(String.valueOf(m.getName()));
                                    machine.setModel(String.valueOf(m.getModel()));
                                    machine.setManufactured(Integer.valueOf(m.getManufactured()));
                                    machine.setSerialNumber(String.valueOf(m.getSerialNumber()));
                                    Department departmentByName = departmentService.findDepartmentByName(m.getDepartment());
                                    machine.setDepartment(departmentByName);
                                    machine.setStatus(m.getStatus());
                                    machine.setInstallDate(LocalDateTime.now());
                                    LOGGER.info("convertedMachine(...)");
                                    return machine;
                                }
                        )
                        .toList();

        LOGGER.info("machineDataExcelConverter(...)");
        return convertedMachines;
    }
}
