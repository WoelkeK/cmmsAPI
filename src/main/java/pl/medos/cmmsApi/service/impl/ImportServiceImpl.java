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
import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.model.*;
import pl.medos.cmmsApi.repository.HardwareRepository;
import pl.medos.cmmsApi.service.DepartmentService;
import pl.medos.cmmsApi.service.ImportService;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.logging.Logger;

@Service
public class ImportServiceImpl implements ImportService {

    private static final Logger LOGGER = Logger.getLogger(ImportServiceImpl.class.getName());

    private List<String> persons = new ArrayList<>(Arrays.asList("id", "name", "phone", "email", "position", "department"));
    private List<String> departments = new ArrayList<>(Arrays.asList("id", "name", "location"));
    private List<String> machines = new ArrayList<>(Arrays.asList("id", "name", "model", "manufactured", "serialNumber", "department", "status"));
    private List<String> hardwares = new ArrayList<>(Arrays.asList(
            "inventoryNo", "department", "status", "employee", "type", "name", "installDate", "invoiceNo", "systemNo", "serialNumber", "netBios", "ipAddress", "macAddress", "officeName", "officeNo", "activateDate", "description","bitLockKey","bitRecoveryKey"));
    private final HardwareRepository hardwareRepository;

    public ImportServiceImpl(HardwareRepository hardwareRepository) {
        this.hardwareRepository = hardwareRepository;
    }

    public List<Employee> importExcelEmployeesData(MultipartFile fileName) throws IOException {

        LOGGER.info("importExcelEmployeesData()");
        List<Person> rawDataList = new ArrayList<>();

        InputStream file = new BufferedInputStream(fileName.getInputStream());
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
//                            rowDataMap.put(persons.get(k), cell.getStringCellValue());
                            rowDataMap.put(persons.get(k), cell.getStringCellValue().replaceAll(" ", " ").trim());
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
        List<Employee> employees = employeeDataExcelConverter(rawDataList);
        LOGGER.info("importExcelEmployeesData(...)");
        return employees;
    }

    @Override
    public List<Department> importExcelDepartmentsData(MultipartFile fileName) throws IOException {

        LOGGER.info("importExcelDepartmentsData()");

        List<Department> rawDataList = new ArrayList<>();
        InputStream file = new BufferedInputStream(fileName.getInputStream());

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
                            rowDataMap.put(departments.get(k), NumberToTextConverter.toText(cell.getNumericCellValue()));
                            break;
                        case STRING:
//                            rowDataMap.put(persons.get(k), cell.getStringCellValue());
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
                            rowDataMap.put(machines.get(k), cell.getStringCellValue().replaceAll(" ", "").trim());
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

    @Override
    public List<Hardware> importExcelHardwareData(MultipartFile fileName) throws IOException {
        LOGGER.info("importExcelHardwareData()");

        List<JsonHardware> rawDataList = new ArrayList<>();
        InputStream file = new BufferedInputStream(fileName.getInputStream());

        IOUtils.setByteArrayMaxOverride(Integer.MAX_VALUE);
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            String empty = "-----";

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
                        case BLANK:
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

    private List<Employee> employeeDataExcelConverter(List<Person> persons) {
        LOGGER.info("employeeDataExcelConverter()");

        List<Employee> employees =
                persons.stream().map(m -> {

                                    Employee employee = new Employee();
                                    employee.setId(Long.parseLong(String.valueOf(m.getId())));
                                    employee.setName(String.valueOf(m.getName()));
                                    employee.setPhone(String.valueOf(m.getPhone()));
                                    employee.setPosition(String.valueOf(m.getPosition()));
                                    employee.setEmail(String.valueOf(m.getEmail()));
                                    Department department = new Department();
                                    department.setId(Long.valueOf(m.getDepartment()));
                                    employee.setDepartment(department);

                                    LOGGER.info("departmentNameNull (...)");
                                    return employee;
                                }
                        )
                        .toList();

        LOGGER.info("employeeDataExcelConverter(...)");
        return employees;
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

    private List<Machine> machineDataExcelConverter(List<MachineDep> machineDeps) {
        LOGGER.info("employeeDataExcelConverter()");

        List<Machine> convertedMachines =
                machineDeps.stream().map(m -> {

                                    Machine machine = new Machine();
                                    machine.setId(Long.parseLong((String.valueOf(m.getId()))));
                                    machine.setName(String.valueOf(m.getName()));
                                    machine.setModel(String.valueOf(m.getModel()));
                                    machine.setManufactured(Integer.valueOf(m.getManufactured()));
                                    machine.setSerialNumber(String.valueOf(m.getSerialNumber()));
                                    Department department = new Department();
                                    department.setId(Long.valueOf(m.getDepartment()));
                                    machine.setDepartment(department);
                                    machine.setStatus(m.getStatus());
                                    machine.setInstallDate(LocalDateTime.now());

                                    LOGGER.info("departmentNameNull (...)");
                                    return machine;
                                }
                        )
                        .toList();

        LOGGER.info("employeeDataExcelConverter(...)");
        return convertedMachines;
    }

    private List<Hardware> hardwareDataExcelConverter(List<JsonHardware> hardwares) {
        LOGGER.info("hardwareDataExcelConverter()");

        List<Hardware> convertedHardwares =
                hardwares.stream().map(m -> {
                                    LOGGER.info("Row create()");
                                    Hardware hardware = new Hardware();

//                                    hardware.setId(Long.parseLong((String.valueOf(m.getId()))));
                                    hardware.setInventoryNo(m.getInventoryNo());
                                    hardware.setDepartment(m.getDepartment());
                                    hardware.setStatus(m.getStatus());
                                    hardware.setEmployee(m.getEmployee());
                                    hardware.setType(m.getType());
                                    hardware.setName(m.getName());

//                                    hardware.setInstallDate(LocalDate.now());
                                    LocalDate installDate = convertDate(m.getInstallDate());
                                    hardware.setInstallDate(installDate);

                                    hardware.setInvoiceNo(m.getInvoiceNo());
                                    hardware.setSystemNo(m.getSystemNo());
                                    hardware.setSerialNumber(m.getSerialNumber());
                                    hardware.setNetBios(m.getNetBios());
                                    hardware.setIpAddress(m.getIpAddress());
                                    hardware.setMacAddress(m.getMacAddress());
                                    hardware.setOfficeName(m.getOfficeName());
                                    hardware.setOfficeNo(m.getOfficeNo());
                                    hardware.setBitLockKey(m.getBitLockKey());
                                    hardware.setBitRecoveryKey(m.getBitRecoveryKey());

                                    LocalDate activateDate = convertDate(m.getActivateDate());
                                    hardware.setActivateDate(activateDate);

                                    hardware.setDescription(m.getDescription());

                                    LOGGER.info("Row create(...)");
                                    return hardware;
                                }
                        )
                        .toList();

        LOGGER.info("hardwareDataExcelConverter(...)");
        return convertedHardwares;
    }

    private LocalDate convertDate(String date) {
        LOGGER.info("DataXLS_String " + date);

        if (date == null || date.contains("-----")) {
            LocalDate defaultDate = LocalDate.of(1000, 1, 1);
            return defaultDate;
        }else{

            LOGGER.info("Start parsing string to date " + date.toString());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM d H:mm:ss zzz yyyy", Locale.ENGLISH);
            ZonedDateTime parseDate = ZonedDateTime.parse(date, formatter);
            LocalDate localDate = parseDate.toLocalDate();
            LOGGER.info("DataXLS_LocalDate " + localDate);
            return localDate;
        }
    }
}

