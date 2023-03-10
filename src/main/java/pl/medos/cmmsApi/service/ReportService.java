package pl.medos.cmmsApi.service;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.repository.MachineRepository;
import pl.medos.cmmsApi.repository.entity.MachineEntity;

import java.util.List;


@Service
public class ReportService {

	@Autowired
	private MachineRepository machineRepository;

	public void generateExcel(HttpServletResponse response) throws Exception {

		List<MachineEntity> machineEntities= machineRepository.findAll();

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Info o maszynie");
		HSSFRow row = sheet.createRow(0);

		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("Nazwa");
		row.createCell(2).setCellValue("Model");
		row.createCell(3).setCellValue("S/N");
		row.createCell(4).setCellValue("Rok");
		row.createCell(5).setCellValue("Stan");

		int dataRowIndex = 1;

		for (MachineEntity machineEntity : machineEntities) {
			HSSFRow dataRow = sheet.createRow(dataRowIndex);
			dataRow.createCell(0).setCellValue(machineEntity.getId());
			dataRow.createCell(1).setCellValue(machineEntity.getName());
			dataRow.createCell(2).setCellValue(machineEntity.getModel());
			dataRow.createCell(3).setCellValue(machineEntity.getSerialNumber());
			dataRow.createCell(4).setCellValue(machineEntity.getManufactured());
			dataRow.createCell(5).setCellValue(machineEntity.getStatus());
			dataRowIndex++;
		}

		ServletOutputStream ops = response.getOutputStream();
		workbook.write(ops);
		workbook.close();
		ops.close();

	}

}
