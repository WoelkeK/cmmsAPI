package pl.medos.cmmsApi.service.impl;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.model.Hardware;
import pl.medos.cmmsApi.model.Machine;
import pl.medos.cmmsApi.service.ExportService;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ExportToServiceImpl implements ExportService {

    private List<Machine> machines;
    private List<Hardware> hardwares;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    @Override
    public void excelMachineModelGenerator(List<Machine> machines) {
        this.machines = machines;
        workbook = new XSSFWorkbook();
    }

    @Override
    public void excelHardwaresModelGenerator(List<Hardware> hardwares) {
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
    }

    private void writeHardwareHeader() {
        sheet = workbook.createSheet("Urządzenia");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "ID", style);
        createCell(row, 1, "Numer inwentarzowy", style);
        createCell(row, 2, "Dział", style);
        createCell(row, 3, "Status", style);
        createCell(row, 4, "Użytkownik", style);
        createCell(row, 5, "Typ urządzenia.", style);
        createCell(row, 6, "Nazwa.", style);
        createCell(row, 7, "Data zakupu", style);
        createCell(row, 8, "Dokument zakupu", style);
        createCell(row, 9, "OS", style);
        createCell(row, 10, "SN / Service Tag ", style);
        createCell(row, 11, "netBios", style);
        createCell(row, 12, "IP", style);
        createCell(row, 13, "MAC", style);
    }

    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else {
            cell.setCellValue((Boolean) valueOfCell);
        }
        cell.setCellStyle(style);
    }

    private void writeMachine() {
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
    }

    private void writeHardware() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (Hardware record : hardwares) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, record.getId(), style);
            createCell(row, columnCount++, record.getInventoryNo(), style);
            createCell(row, columnCount++, record.getDepartment().getName(), style);
            createCell(row, columnCount++, record.getStatus(), style);
            createCell(row, columnCount++, record.getEmployee().getName(), style);
            createCell(row, columnCount++, record.getType(), style);
            createCell(row, columnCount++, record.getName(), style);
            createCell(row, columnCount++, record.getInstallDate().format(DateTimeFormatter.ISO_LOCAL_DATE).toString(), style);
            createCell(row, columnCount++, record.getInvoiceNo(), style);
            createCell(row, columnCount++, record.getSystemNo(), style);
            createCell(row, columnCount++, record.getSerialNumber(), style);
            createCell(row, columnCount++, record.getNetBios(), style);
            createCell(row, columnCount++, record.getIpAddress(), style);
            createCell(row, columnCount++, record.getMacAddress(), style);
        }
    }
}
