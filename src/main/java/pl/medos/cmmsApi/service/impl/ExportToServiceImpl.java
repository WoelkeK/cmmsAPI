package pl.medos.cmmsApi.service.impl;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.model.*;
import pl.medos.cmmsApi.service.ExportService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ExportToServiceImpl implements ExportService {

    private static final Logger LOGGER = Logger.getLogger(ExportToServiceImpl.class.getName());
    private List<Machine> machines;
    private List<Hardware> hardwares;
    private List<Employee> employees;
    private List<Job> jobs;
    private List<Notification> notifications;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    @Override
    public void excelMachineModelGenerator(List<Machine> machines) {
        this.machines = machines;
        workbook = new XSSFWorkbook();
    }

    @Override
    public void excelHardwaresModelGenerator(List<Hardware> hardwares) {
        LOGGER.info("excelHardware()" + hardwares.isEmpty());
        this.hardwares = hardwares;
        workbook = new XSSFWorkbook();
    }

    @Override
    public void generateExcelFile(HttpServletResponse response) throws IOException {

        writeMachineHeader();
        writeMachine();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    @Override
    public void generateExcelHardwareFile(HttpServletResponse response) throws IOException {
        writeHardwareHeader();
        writeHardware();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    @Override
    public void excelJobsModelGenerator(List<Job> jobs) {
        LOGGER.info("jobExcelModelGenerator()");
        this.jobs = jobs;
        workbook = new XSSFWorkbook();
    }

    @Override
    public void generateExcelJobFile(HttpServletResponse response) throws IOException {
        LOGGER.info("jobExcelGenerate()");
        writeJobHeader();
        writeJob();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    @Override
    public void excelEmployeeModelGenerator(List<Employee> employees) {
        this.employees = employees;
        workbook = new XSSFWorkbook();
    }

    @Override
    public void generateExcelEmployeeFile(HttpServletResponse response) throws IOException {
        writeEmployeeHeader();
        writeEmployee();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

    }

    @Override
    public void excelNotificationModelGenerator(List<Notification> notifications) {
        this.notifications = notifications;
        workbook = new XSSFWorkbook();
    }

    @Override
    public void generateExcelNotificationFile(HttpServletResponse response) throws IOException {
        writeNotificationHeader();
        writeNotification();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }


    private void writeNotificationHeader() {

        sheet = workbook.createSheet("Awizacje");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "Data", style);
        createCell(row, 1, "Kategoria", style);
        createCell(row, 2, "Status", style);
        createCell(row, 3, "Firma", style);
        createCell(row, 4, "Towar", style);
        createCell(row, 5, "Ilość", style);
        createCell(row, 6, "Nr rejestracyjny", style);
        createCell(row, 7, "Kierowca", style);
        createCell(row, 8, "Telefon kierowcy", style);
        createCell(row, 9, "Osoba kontaktowa", style);
        createCell(row, 10, "Telefon pracownika", style);
        createCell(row, 11, "Informacje", style);

        LOGGER.info("Header create complete! ");

    }

    private void writeNotification() {
        LOGGER.info("writeEmployee()");
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        CellStyle dateCellStyle = workbook.createCellStyle();
        short dateFormat = workbook.createDataFormat().getFormat("dd/mm/yyyy hh:mm");
        dateCellStyle.setDataFormat(dateFormat);

        font.setFontHeight(14);
        style.setFont(font);
        dateCellStyle.setFont(font);
        for (Notification record : notifications) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, record.getVisitDate(), dateCellStyle);
            createCell(row, columnCount++, record.getType().toString(), style);
            createCell(row, columnCount++, record.getStatus().toString(), style);
            createCell(row, columnCount++, record.getSupplier(), style);
            createCell(row, columnCount++, record.getItem(), style);
            createCell(row, columnCount++, record.getItemDetails(), style);
            createCell(row, columnCount++, record.getCarPlates(), style);
            createCell(row, columnCount++, record.getDriverName(), style);
            createCell(row, columnCount++, record.getDriverPhone(), style);
            createCell(row, columnCount++, record.getEmployee(), style);
            createCell(row, columnCount++, record.getEmployeePhone(), style);
            createCell(row, columnCount++, record.getDescription(), style);
        }
        LOGGER.info("writeEmployees(...)");
    }

    private void writeEmployee() {
        LOGGER.info("writeEmployee()");
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (Employee record : employees) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, record.getName(), style);
            createCell(row, columnCount++, record.getPosition(), style);
            createCell(row, columnCount++, record.getDepartment().getName(), style);
            createCell(row, columnCount++, record.getPhone(), style);
            createCell(row, columnCount++, record.getEmail(), style);

            if (record.getProfile() == null) {
                createCell(row, columnCount++, true, style);
            } else {
                createCell(row, columnCount++, record.getProfile(), style);
            }
        }
        LOGGER.info("writeEmployees(...)");
    }

    private void writeEmployeeHeader() {
        sheet = workbook.createSheet("Pracownicy");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "Imię i Nazwisko", style);
        createCell(row, 1, "Stanowisko", style);
        createCell(row, 2, "Wydział", style);
        createCell(row, 3, "Telefon", style);
        createCell(row, 4, "Email.", style);
        createCell(row, 5, "Status.", style);
        LOGGER.info("Header create complete! ");
    }


    private void writeJob() {
        LOGGER.info("writeJob()");

        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        CellStyle dateCellStyle = workbook.createCellStyle();
        short dateFormat = workbook.createDataFormat().getFormat("dd/mm/yyyy hh:mm");
        dateCellStyle.setDataFormat(dateFormat);

        font.setFontHeight(14);
        style.setFont(font);
        dateCellStyle.setFont(font);
        for (Job record : jobs) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            if (record.getRequestDate() == null) {
                createCell(row, columnCount++, record.getRequestDate(), style);
            } else {
                createCell(row, columnCount++, record.getRequestDate(), dateCellStyle);
            }
            createCell(row, columnCount++, record.getEmployee().getName(), style);

            if (record.getEngineer() == null) {

                createCell(row, columnCount++, null, style);
            } else {
                createCell(row, columnCount++, record.getEngineer().getName(), style);
            }

            createCell(row, columnCount++, record.getDepartment().getName(), style);
            createCell(row, columnCount++, record.getMachine().getName(), style);
            createCell(row, columnCount++, record.getMessage(), style);
            createCell(row, columnCount++, record.getSolution(), style);

            if (record.getJobStartTime() == null) {
                createCell(row, columnCount++, record.getJobStartTime(), style);
            } else {
                createCell(row, columnCount++, record.getJobStartTime(), dateCellStyle);
            }

            if (record.getJobStopTime() == null) {
                createCell(row, columnCount++, record.getJobStopTime(), style);
            } else {
                createCell(row, columnCount++, record.getJobStopTime(), dateCellStyle);
            }

            if (record.getJobStatus() != null) {

                createCell(row, columnCount++, record.getJobStatus().toString(), style);
            } else {
                createCell(row, columnCount++, null, style);

            }

            if (record.getJobShedule() == null) {
                createCell(row, columnCount++, record.getJobShedule(), style);
            } else {
                createCell(row, columnCount++, record.getJobShedule(), dateCellStyle);
            }

            createCell(row, columnCount++, record.getDecision().toString(), style);

            if (record.getDateOffset() != null) {
                createCell(row, columnCount++, record.getDateOffset().toString(), style);
            } else {
                createCell(row, columnCount++, null, style);

            }
            createCell(row, columnCount++, record.getStatus(), style);
            createCell(row, columnCount++, record.getOffset(), style);

            if (record.getPhotoFileName() == null || record.getPhotoFileName().isBlank()) {
                record.setPhotoFileName("default.jpg");
            } else {

                createCell(row, columnCount++, record.getPhotoFileName(), style);
            }

        }
        LOGGER.info("writeJob(...)");
    }

    private void writeJobHeader() {
        sheet = workbook.createSheet("Awarie");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "Data zgłoszenia", style);
        createCell(row, 1, "Zgłaszający", style);
        createCell(row, 2, "Mechanik", style);
        createCell(row, 3, "Dział", style);
        createCell(row, 4, "Maszyna", style);
        createCell(row, 5, "Usterka", style);
        createCell(row, 6, "Opis prac", style);
        createCell(row, 7, "Rozpoczęcie prac", style);
        createCell(row, 8, "Zakończenie prac", style);
        createCell(row, 9, "Typ zgłoszenia", style);
        createCell(row, 10, "Data przeglądu", style);
        createCell(row, 11, "Cykliczny", style);
        createCell(row, 12, "Jednostka", style);
        createCell(row, 13, "Status", style);
        createCell(row, 14, "Przesunięcie", style);
        createCell(row, 15, "Zdjęcie", style);

        LOGGER.info("Header create complete! ");
    }

    private void writeMachineHeader() {
        sheet = workbook.createSheet("Maszyny");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "Nazwa", style);
        createCell(row, 1, "Model", style);
        createCell(row, 2, "Rok", style);
        createCell(row, 3, "S/N", style);
        createCell(row, 4, "Data Instalacji.", style);
        createCell(row, 5, "Stan.", style);
        createCell(row, 6, "Wydział.", style);
        LOGGER.info("Header create complete! ");
    }

    private void writeHardwareHeader() {
        sheet = workbook.createSheet("Urządzenia");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
//        createCell(row, 0, "ID", style);
        createCell(row, 0, "Numer inwentarzowy", style);
        createCell(row, 1, "Dział             ", style);
        createCell(row, 2, "Stan urządzenia", style);
        createCell(row, 3, "Pracownik                      ", style);
        createCell(row, 4, "Typ urządzenia", style);
        createCell(row, 5, "Model urządzaenia", style);
        createCell(row, 6, "Data zakupu", style);
        createCell(row, 7, "Dokument zakupu", style);
        createCell(row, 8, "System operacyjny", style);
        createCell(row, 9, "Numer seryjny ", style);
        createCell(row, 10, "netBios", style);
        createCell(row, 11, "Adres IP", style);
        createCell(row, 12, "Adres MAC", style);
        createCell(row, 13, "Wersja Office", style);
        createCell(row, 14, "Klucz / Konto                  ", style);
        createCell(row, 15, "Data aktywacji", style);
        createCell(row, 16, "Opis dodatkowy                  ", style);
        createCell(row, 17, "Identyfikator szyfrowania                 ", style);
        createCell(row, 18, "Klucz szyfrujący                  ", style);
        createCell(row, 19, "Uprawnienia Awizacje", style);


        /*Uprawnienia*/

        createCell(row, 20, "Awizacje Odczyt", style);
        createCell(row, 21, "Awizacje Zapis", style);
        createCell(row, 22, "Awizacje Usuwanie", style);
        createCell(row, 23, "Pracownicy Odczyt", style);
        createCell(row, 24, "Pracownicy Zapis", style);
        createCell(row, 25, "Pracownicy Usuwanie", style);
        createCell(row, 26, "Przepustki Odczyt", style);
        createCell(row, 27, "Przepustki Zapis", style);
        createCell(row, 28, "Przepustki Usuwanie", style);
        createCell(row, 29, "Wydziały Odczyt", style);
        createCell(row, 30, "Wydziały  Zapis", style);
        createCell(row, 31, "Wydziały  Usuwanie", style);
        createCell(row, 32, "Maszyny Odczyt", style);
        createCell(row, 33, "Maszyny Zapis", style);
        createCell(row, 34, "Maszyny Usuwanie", style);
        createCell(row, 35, "Awarie Odczyt", style);
        createCell(row, 36, "Awarie Zapis", style);
        createCell(row, 37, "Awarie Usuwanie", style);


        LOGGER.info("Header create complete! " + sheet.getPhysicalNumberOfRows() + " \n");
    }

    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {

        LOGGER.info("createCell()");
        String empty = " ";
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell != null) {

            if (valueOfCell instanceof Integer) {
                cell.setCellValue((Integer) valueOfCell);
            } else if (valueOfCell instanceof Long) {
                cell.setCellValue((Long) valueOfCell);
            } else if (valueOfCell instanceof Boolean) {
                cell.setCellValue((Boolean) valueOfCell);
            } else if (valueOfCell instanceof LocalDate) {
                cell.setCellValue((LocalDate) valueOfCell);
            } else if (valueOfCell instanceof LocalDateTime) {
                cell.setCellValue((LocalDateTime) valueOfCell);
            } else {
                LOGGER.info("Cell value: " + valueOfCell.toString());
                cell.setCellValue((String) valueOfCell);
            }
            cell.setCellStyle(style);
            LOGGER.info("createCell(...)");
        } else {
            cell.setCellValue(empty);
        }
    }

    private void writeMachine() {
        LOGGER.info("writeMachine()");
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (Machine record : machines) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, record.getName(), style);
            createCell(row, columnCount++, record.getModel(), style);
            createCell(row, columnCount++, record.getManufactured(), style);
            createCell(row, columnCount++, record.getSerialNumber(), style);
            createCell(row, columnCount++, record.getInstallDate().format(DateTimeFormatter.ISO_LOCAL_DATE).toString(), style);
            createCell(row, columnCount++, record.getStatus(), style);
            createCell(row, columnCount++, record.getDepartment().getName(), style);
        }
        LOGGER.info("writeMachine(...)");

    }

    private void writeHardware() {
        LOGGER.info("writeHardware()");
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();

        // Create a cell style with date format
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("dd/mm/yyyy"));

        font.setFontHeight(14);
        style.setFont(font);
        dateCellStyle.setFont(font);
        for (Hardware record : hardwares) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, record.getInventoryNo(), style);
            createCell(row, columnCount++, record.getDepartment(), style);
            createCell(row, columnCount++, record.getStatus().toString(), style);
            createCell(row, columnCount++, record.getEmployee(), style);
            createCell(row, columnCount++, record.getType().toString(), style);
            createCell(row, columnCount++, record.getName(), style);

            if (record.getInstallDate() == null) {
                createCell(row, columnCount++, record.getInstallDate(), style);
            } else {
                createCell(row, columnCount++, record.getInstallDate(), dateCellStyle);
            }
            createCell(row, columnCount++, record.getInvoiceNo(), style);
            createCell(row, columnCount++, record.getSystemNo(), style);
            createCell(row, columnCount++, record.getSerialNumber(), style);
            createCell(row, columnCount++, record.getNetBios(), style);
            createCell(row, columnCount++, record.getIpAddress(), style);
            createCell(row, columnCount++, record.getMacAddress(), style);
            createCell(row, columnCount++, record.getOfficeName(), style);
            createCell(row, columnCount++, record.getOfficeNo(), style);
            if (record.getActivateDate() == null) {
                createCell(row, columnCount++, record.getActivateDate(), style);
            } else {
                createCell(row, columnCount++, record.getActivateDate(), dateCellStyle);
            }
            createCell(row, columnCount++, record.getDescription(), style);
            createCell(row, columnCount++, record.getBitLockKey(), style);
            createCell(row, columnCount++, record.getBitRecoveryKey(), style);

            if (record.getPermission() == null) {
                createCell(row, columnCount++, "USER", style);
            } else {

                createCell(row, columnCount++, record.getPermission().toString(), style);
            }


            createCell(row, columnCount++, record.isNRead(), style);
            createCell(row, columnCount++, record.isNEdit(), style);
            createCell(row, columnCount++, record.isNDelete(), style);

            createCell(row, columnCount++, record.isERead(), style);
            createCell(row, columnCount++, record.isEEdit(), style);
            createCell(row, columnCount++, record.isEDelete(), style);

            createCell(row, columnCount++, record.isPRead(), style);
            createCell(row, columnCount++, record.isPEdit(), style);
            createCell(row, columnCount++, record.isPDelete(), style);

            createCell(row, columnCount++, record.isDRead(), style);
            createCell(row, columnCount++, record.isDEdit(), style);
            createCell(row, columnCount++, record.isDDelete(), style);

            createCell(row, columnCount++, record.isMRead(), style);
            createCell(row, columnCount++, record.isMEdit(), style);
            createCell(row, columnCount++, record.isMDelete(), style);

            createCell(row, columnCount++, record.isJRead(), style);
            createCell(row, columnCount++, record.isJEdit(), style);
            createCell(row, columnCount++, record.isJDelete(), style);

        }
        LOGGER.info("writeHardware(...)");
    }
}
