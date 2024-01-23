//package pl.medos.cmmsApi.service.impl;
//
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.util.NumberToTextConverter;
//import org.apache.poi.util.IOUtils;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import pl.medos.cmmsApi.enums.Permission;
//import pl.medos.cmmsApi.model.*;
//import pl.medos.cmmsApi.repository.DepartmentRepository;
//import pl.medos.cmmsApi.repository.HardwareRepository;
//import pl.medos.cmmsApi.repository.entity.DepartmentEntity;
//import pl.medos.cmmsApi.service.*;
//
//import java.io.*;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.time.ZonedDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//import java.util.logging.Logger;
//
//@Service
//public class ImportServiceImpl implements ImportService {
//
//    @Autowired
//    private DepartmentService departmentService;
//    @Autowired
//    private MachineService machineService;
//    @Autowired
//    private EmployeeService employeeService;
//    @Autowired
//    private ImageService imageService;
//
//
//    private static final Logger LOGGER = Logger.getLogger(ImportServiceImpl.class.getName());
//
//    private List<String> persons = new ArrayList<>(Arrays.asList("id", "name", "phone", "email", "position", "department"));
//
//
//
//    private final HardwareRepository hardwareRepository;
//
//    public ImportServiceImpl(HardwareRepository hardwareRepository) {
//        this.hardwareRepository = hardwareRepository;
//    }
//
//
//
//
//
//
//    private List<Hardware> hardwareDataExcelConverter(List<JsonHardware> hardwares) {
//        LOGGER.info("hardwareDataExcelConverter()");
//
//        List<Hardware> convertedHardwares =
//                hardwares.stream().map(m -> {
//                                    LOGGER.info("Row create()");
//                                    Hardware hardware = new Hardware();
//                                    hardware.setInventoryNo(m.getInventoryNo());
//                                    hardware.setDepartment(m.getDepartment());
//                                    hardware.setStatus(m.getStatus());
//                                    hardware.setEmployee(m.getEmployee());
//                                    hardware.setType(m.getType());
//                                    hardware.setName(m.getName());
//                                    hardware.setInvoiceNo(m.getInvoiceNo());
//                                    hardware.setSystemNo(m.getSystemNo());
//                                    hardware.setSerialNumber(m.getSerialNumber());
//                                    hardware.setNetBios(m.getNetBios());
//                                    hardware.setIpAddress(m.getIpAddress());
//                                    hardware.setMacAddress(m.getMacAddress());
//                                    hardware.setOfficeName(m.getOfficeName());
//                                    hardware.setOfficeNo(m.getOfficeNo());
//                                    hardware.setBitLockKey(m.getBitLockKey());
//                                    hardware.setBitRecoveryKey(m.getBitRecoveryKey());
//                                    hardware.setDescription(m.getDescription());
//
//                                    if (m.getPermission() == null) {
//                                        hardware.setPermission(Permission.USER);
//                                    } else {
//
//                                        hardware.setPermission(m.getPermission());
//                                    }
//
//                                    if (m.getInstallDate() == null || m.getInstallDate().isEmpty()) {
//                                        hardware.setInstallDate(null);
//                                    } else {
//                                        LocalDate installDate = convertDate(m.getInstallDate());
//                                        hardware.setInstallDate(installDate);
//                                        LOGGER.info(installDate.toString());
//                                    }
//                                    if (m.getActivateDate() == null || m.getActivateDate().isEmpty()) {
//                                        hardware.setActivateDate(null);
//                                    } else {
//                                        LocalDate activateDate = convertDate(m.getActivateDate());
//                                        hardware.setActivateDate(activateDate);
//                                    }
//
//                                    LOGGER.info("Row create(...)");
//                                    return hardware;
//                                }
//                        )
//                        .toList();
//
//        LOGGER.info("hardwareDataExcelConverter(...)");
//        return convertedHardwares;
//    }
//
//    private LocalDate convertDate(String date) {
//        LOGGER.info("DataXLS_String " + date);
//        LOGGER.info("Start parsing string to date " + date.toString());
//
//        DateTimeFormatter inputDateFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
//        LocalDate localDate = LocalDate.parse(date, inputDateFormatter);
//        DateTimeFormatter outputDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//        String outputDateString = localDate.format(outputDateFormatter);
//        LOGGER.info("ConvertedDate() " + localDate.toString());
//        return localDate;
//    }
//
//    private LocalDateTime convertDateTime(String date) {
//        LOGGER.info("DataXLS_String " + date);
//        LOGGER.info("Start parsing string to date " + date.toString());
//
//        DateTimeFormatter inputDateFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
//        LocalDateTime localDate = LocalDateTime.parse(date, inputDateFormatter);
//        DateTimeFormatter outputDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//        String outputDateString = localDate.format(outputDateFormatter);
//        LOGGER.info("ConvertedDate() " + localDate.toString());
//        return localDate;
//    }
//}
