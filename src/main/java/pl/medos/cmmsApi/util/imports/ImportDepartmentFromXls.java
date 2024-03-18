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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Logger;

@Component
public class ImportDepartmentFromXls implements ImportDepartment{

    private static final Logger LOGGER = Logger.getLogger(ImportDepartmentFromXls.class.getName());
    private List<String> departments = new ArrayList<>(Arrays.asList("id", "name", "location"));

    @Override
    public List<Department> importExcelDepartmentsData(MultipartFile fileName) throws IOException {

        LOGGER.info("importExcelDepartmentsData()");
        List<Department> rawDataList = new ArrayList<>();
        InputStream file = new BufferedInputStream(fileName.getInputStream());

        IOUtils.setByteArrayMaxOverride(Integer.MAX_VALUE);
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);
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
                            rowDataMap.put(departments.get(k), NumberToTextConverter.toText(cell.getNumericCellValue()));
                            break;
                        case STRING:
                            rowDataMap.put(departments.get(k), cell.getStringCellValue().replaceAll(" ", "").trim());
                            break;
                    }
                }
            }
            ObjectMapper mapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
            Department rawData = mapper.convertValue(rowDataMap, Department.class);
            rawDataList.add(rawData);
            LOGGER.info("rawData " + rawData);
        }
        List<Department> departments = departmentsDataExcelConverter(rawDataList);
        return departments;
    }

    public List<Department> departmentsDataExcelConverter(List<Department> departments) {
        LOGGER.info("employeeDataExcelConverter()");
        List<Department> departmentsCon =
                departments.stream().map(m -> {

                            Department department = new Department();
                            department.setId(Long.parseLong(String.valueOf(m.getId())));
                            department.setName(String.valueOf(m.getName()));
                            department.setLocation(m.getLocation());
                            return department;
                        })
                        .toList();

        LOGGER.info("employeeDataExcelConverter(...)");
        return departments;
    }
}
