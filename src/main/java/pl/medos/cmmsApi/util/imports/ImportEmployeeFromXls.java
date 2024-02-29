package pl.medos.cmmsApi.util.imports;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.model.Person;
import pl.medos.cmmsApi.service.DepartmentService;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Logger;

@Component
public class ImportEmployeeFromXls implements ImportEmployee {

    private static final Logger LOGGER = Logger.getLogger(ImportHardwareFromXls.class.getName());
    @Autowired
    private DepartmentService departmentService;
    private List<String> persons = new ArrayList<>(Arrays.asList("name", "position", "department", "phone", "email", "profile"));

    @Override
    public List<Employee> importExcelEmployeesData(MultipartFile fileName) throws IOException {

        LOGGER.info("importExcelEmployeesData()");
        List<Person> rawDataList = new ArrayList<>();

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
                            rowDataMap.put(persons.get(k), NumberToTextConverter.toText(cell.getNumericCellValue()));
                            break;
                        case STRING:
                            rowDataMap.put(persons.get(k), cell.getStringCellValue().replaceAll(" ", " ").trim());
                            break;
                        case BOOLEAN:
                            rowDataMap.put(persons.get(k), String.valueOf(cell.getBooleanCellValue()));
                    }
                }
            }
            ObjectMapper mapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

            Person rawData = mapper.convertValue(rowDataMap, Person.class);
            rawDataList.add(rawData);
        }
        List<Employee> employees = employeeDataExcelConverter(rawDataList);
        LOGGER.info("importExcelEmployeesData(...)");
        return employees;
    }

    private List<Employee> employeeDataExcelConverter(List<Person> persons) {
        LOGGER.info("employeeDataExcelConverter()");

        List<Employee> employees =
                persons.stream().map(m -> {

                                    Employee employee = new Employee();

                                    employee.setName(String.valueOf(m.getName()));
                                    employee.setPosition(String.valueOf(m.getPosition()));

                                    Department departmentByName = departmentService.findDepartmentByName(m.getDepartment());
                                    employee.setDepartment(departmentByName);
                                    employee.setPhone(String.valueOf(m.getPhone()));
                                    employee.setEmail(String.valueOf(m.getEmail()));

                                    if (m.getProfile() == null) {
                                        employee.setProfile(true);
                                    } else {
                                        employee.setProfile(m.getProfile());
                                    }
                                    LOGGER.info("departmentNameNull (...)");
                                    return employee;
                                }
                        )
                        .toList();

        LOGGER.info("employeeDataExcelConverter(...)");
        return employees;
    }
}
