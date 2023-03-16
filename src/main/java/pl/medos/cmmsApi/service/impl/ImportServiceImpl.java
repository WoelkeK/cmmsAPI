package pl.medos.cmmsApi.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.model.Cost;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.model.Person;
import pl.medos.cmmsApi.repository.entity.CostEntity;
import pl.medos.cmmsApi.service.ImportService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ImportServiceImpl implements ImportService {

    private static final Logger LOGGER = Logger.getLogger(ImportServiceImpl.class.getName());

    private List<String> persons = new ArrayList<>(Arrays.asList("id", "name", "phone", "email", "position", "department"));

    public List<Employee> importExcelData(String fileName) throws IOException {

        List<Person> rawDataList = new ArrayList<>();

        FileInputStream file = new FileInputStream(fileName);
        IOUtils.setByteArrayMaxOverride(Integer.MAX_VALUE);
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Person person = new Person();
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

           Person rawData = mapper.convertValue(rowDataMap, Person.class);
            rawDataList.add(rawData);
        }
        List<Employee> employees = dataExcelConverter(rawDataList);
        return employees;
    }

    private List<Employee> dataExcelConverter(List<Person> persons){
        LOGGER.info("dataExcelConverter()");

        List<Employee> employees =
        persons.stream().map(m-> {
                    Employee employee = new Employee();
                    Department department = new Department();
                    employee.setId(Long.parseLong(String.valueOf(m.getId())));
                    employee.setName(String.valueOf(m.getName()));
                    employee.setPhone(String.valueOf(m.getPhone()));
                    employee.setPosition(String.valueOf(m.getPosition()));
                    employee.setEmail(String.valueOf(m.getEmail()));
                    department.setId(Long.parseLong(String.valueOf(m.getDepartment())));
                    employee.setDepartment(department);

                            return employee;
                        }
                )
                .toList();
        LOGGER.info("dataExcelConverter(...)");
        return  employees;

    }
}

