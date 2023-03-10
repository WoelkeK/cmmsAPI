//package pl.medos.cmmsApi.service;
//
//import jakarta.servlet.ServletOutputStream;
//import jakarta.servlet.http.HttpServletResponse;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import pl.medos.cmmsApi.model.Machine;
//import pl.medos.cmmsApi.repository.MachineRepository;
//import pl.medos.cmmsApi.repository.entity.MachineEntity;
//import pl.medos.cmmsApi.service.mapper.MachineMapper;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//@Service
//public class ReportService {
//
//
//	private MachineRepository machineRepository;
//	private MachineMapper machineMapper;
//
//	public ReportService(MachineRepository machineRepository, MachineMapper machineMapper) {
//		this.machineRepository = machineRepository;
//		this.machineMapper = machineMapper;
//	}
//
//	public void generateExcel(HttpServletResponse response) throws Exception {
//
//		List<MachineEntity> machineEntities= machineRepository.findAll();
//
//		HSSFWorkbook workbook = new HSSFWorkbook();
//		HSSFSheet sheet = workbook.createSheet("Info o maszynie");
//		HSSFRow row = sheet.createRow(0);
//
//		row.createCell(0).setCellValue("ID");
//		row.createCell(1).setCellValue("Nazwa");
//		row.createCell(2).setCellValue("Model");
//		row.createCell(3).setCellValue("S/N");
//		row.createCell(4).setCellValue("Rok");
//		row.createCell(5).setCellValue("Stan");
//
//		int dataRowIndex = 1;
//
//		for (MachineEntity machineEntity : machineEntities) {
//			HSSFRow dataRow = sheet.createRow(dataRowIndex);
//			dataRow.createCell(0).setCellValue(machineEntity.getId());
//			dataRow.createCell(1).setCellValue(machineEntity.getName());
//			dataRow.createCell(2).setCellValue(machineEntity.getModel());
//			dataRow.createCell(3).setCellValue(machineEntity.getSerialNumber());
//			dataRow.createCell(4).setCellValue(machineEntity.getManufactured());
//			dataRow.createCell(5).setCellValue(machineEntity.getStatus());
//			dataRowIndex++;
//		}
//
//		ServletOutputStream ops = response.getOutputStream();
//		workbook.write(ops);
//		workbook.close();
//		ops.close();
//
//	}
//
//	public void generateExcelFromList(List<Machine> machines, HttpServletResponse response) throws Exception {
//
//		List<MachineEntity> machineEntities = machines.stream()
//				.map((machine -> machineMapper.modelToEntity(machine)))
//				.collect(Collectors.toList());
//
////		List<MachineEntity> machineEntities= machineRepository.findAll();
//
//		HSSFWorkbook workbook = new HSSFWorkbook();
//		HSSFSheet sheet = workbook.createSheet("Info o maszynie");
//		HSSFRow row = sheet.createRow(0);
//
//		row.createCell(0).setCellValue("ID");
//		row.createCell(1).setCellValue("Nazwa");
//		row.createCell(2).setCellValue("Model");
//		row.createCell(3).setCellValue("S/N");
//		row.createCell(4).setCellValue("Rok");
//		row.createCell(5).setCellValue("Stan");
//
//		int dataRowIndex = 1;
//
//		for (MachineEntity machineEntity : machineEntities) {
//			HSSFRow dataRow = sheet.createRow(dataRowIndex);
//			dataRow.createCell(0).setCellValue(machineEntity.getId());
//			dataRow.createCell(1).setCellValue(machineEntity.getName());
//			dataRow.createCell(2).setCellValue(machineEntity.getModel());
//			dataRow.createCell(3).setCellValue(machineEntity.getSerialNumber());
//			dataRow.createCell(4).setCellValue(machineEntity.getManufactured());
//			dataRow.createCell(5).setCellValue(machineEntity.getStatus());
//			dataRowIndex++;
//		}
//
//		ServletOutputStream ops = response.getOutputStream();
//		workbook.write(ops);
//		workbook.close();
//		ops.close();
//
//	}
//
//}
