package pl.medos.cmmsApi.service.impl;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.model.Hardware;
import pl.medos.cmmsApi.model.Job;
import pl.medos.cmmsApi.model.Machine;
import pl.medos.cmmsApi.service.ExportService;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ExportToServiceImpl implements ExportService {

    private static final Logger LOGGER = Logger.getLogger(ExportToServiceImpl.class.getName());
    private List<Machine> machines;
    private List<Hardware> hardwares;
    private List<Job> jobs;
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
        this.jobs = jobs;
        workbook = new XSSFWorkbook();
    }

    @Override
    public void generateExcelJobFile(HttpServletResponse response) throws IOException {
        writeJobHeader();
        writeJob();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void writeJob() {
        LOGGER.info("writeJob()");

        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("dd/mm/yyyy"));
        font.setFontHeight(14);
        style.setFont(font);
        for (Job record : jobs) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            if (record.getRequestDate() == null) {
                createCell(row, columnCount++, record.getRequestDate(), style);
            } else {
                createCell(row, columnCount++, record.getRequestDate(),dateCellStyle);
            }
            createCell(row, columnCount++, record.getEmployee(), style);
            createCell(row, columnCount++, record.getDepartment(), style);
            createCell(row, columnCount++, record.getMachine(), style);
            createCell(row, columnCount++, record.getMessage(), style);

            if (record.getJobStartTime() == null) {
                createCell(row, columnCount++, record.getJobStartTime(), style);
            } else {
                createCell(row, columnCount++, record.getJobStopTime(),dateCellStyle);
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
        createCell(row, 2, "Dział", style);
        createCell(row, 3, "Maszyna", style);
        createCell(row, 4, "Usterka.", style);
        createCell(row, 5, "Rozpoczęcie prac.", style);
        createCell(row, 6, "Zakończenie prac", style);
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
        createCell(row, 0, "ID", style);
        createCell(row, 1, "Nazwa", style);
        createCell(row, 2, "Model", style);
        createCell(row, 3, "Rok", style);
        createCell(row, 4, "S/N", style);
        createCell(row, 5, "Data Instalacji.", style);
        createCell(row, 6, "Stan.", style);
        createCell(row, 7, "Wydział.", style);
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
//            } else if (valueOfCell instanceof LocalDate) {
//                cell.setCellValue(java.sql.Date.valueOf(LocalDate.now()));

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
            createCell(row, columnCount++, record.getId(), style);
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
        for (Hardware record : hardwares) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
//            createCell(row, columnCount++, record.getId(), style);
            createCell(row, columnCount++, record.getInventoryNo(), style);
            createCell(row, columnCount++, record.getDepartment(), style);
            createCell(row, columnCount++, record.getStatus().toString(), style);
            createCell(row, columnCount++, record.getEmployee(), style);
            createCell(row, columnCount++, record.getType().toString(), style);
            createCell(row, columnCount++, record.getName(), style);

            if (record.getInstallDate() == null) {
                createCell(row, columnCount++, record.getInstallDate(), style);
            } else {
                createCell(row, columnCount++, record.getInstallDate(),dateCellStyle);
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
            createCell(row, columnCount++, record.getPermission().toString(), style);
        }
        LOGGER.info("writeHardware(...)");
    }

}
