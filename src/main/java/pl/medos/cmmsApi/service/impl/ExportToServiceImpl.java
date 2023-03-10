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
import pl.medos.cmmsApi.repository.entity.MachineEntity;
import pl.medos.cmmsApi.service.ExportService;

import java.io.IOException;
import java.util.List;
@Service
public class ExportToServiceImpl implements ExportService {

    private List<MachineEntity> machineEntities;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    @Override
    public void excelGenerator(List<MachineEntity> machineEntities) {
        this.machineEntities = machineEntities;
        workbook = new XSSFWorkbook();
    }

    @Override
    public void generateExcelFile(HttpServletResponse response) throws IOException {

        writeHeader();
        write();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void writeHeader() {
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
        createCell(row, 5, "Stan.", style);
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

    private void write() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (MachineEntity record : machineEntities) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, record.getId(), style);
            createCell(row, columnCount++, record.getName(), style);
            createCell(row, columnCount++, record.getModel(), style);
            createCell(row, columnCount++, record.getManufactured(), style);
            createCell(row, columnCount++, record.getSerialNumber(), style);
            createCell(row, columnCount++, record.getStatus(), style);
        }
    }
}
