package pl.medos.cmmsApi.service;

import jakarta.servlet.http.HttpServletResponse;
import pl.medos.cmmsApi.repository.entity.MachineEntity;

import java.io.IOException;
import java.util.List;

public interface ExportService {

    void excelGenerator(List<MachineEntity> machineEntities);

    void generateExcelFile(HttpServletResponse response) throws IOException;
}
