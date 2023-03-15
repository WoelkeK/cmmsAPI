package pl.medos.cmmsApi.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.model.Person;
import pl.medos.cmmsApi.service.ImportService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

@Service
public class ImportServiceImpl implements ImportService {

    private static final Logger LOGGER = Logger.getLogger(ImportServiceImpl.class.getName());

    private List<String> persons = new ArrayList<>(Arrays.asList("id", "name", "phone", "email", "position", "department"));

    public List<Employee> importExcelData() throws IOException {

        List<Employee> rawDataList = new ArrayList<>();

        FileInputStream file = new FileInputStream("c:/XL/wykaz.xlsx");
        IOUtils.setByteArrayMaxOverride(Integer.MAX_VALUE);
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Employee person = new Employee();
        Iterator<Row> rowIterator = sheet.iterator();

        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            Map<String, String> rowDataMap = new HashMap<>();
            Cell cell;
            for (int k = 0; k < row.getLastCellNum(); k++) {
                if (null != (cell = row.getCell(k))) {
                    switch (cell.getCellType()) {
                        case NUMERIC:

                            rowDataMap.put(persons.get(k), NumberToTextConverter.toText(cell.getNumericCellValue()));
                            break;
                        case STRING:
                            rowDataMap.put(persons.get(k), cell.getStringCellValue());
                            break;
                    }
                }
            }

            ObjectMapper mapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

            Employee rawData = mapper.convertValue(rowDataMap, Employee.class);
            rawDataList.add(rawData);
        }
        return rawDataList;
    }
}

