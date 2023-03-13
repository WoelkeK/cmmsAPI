package pl.medos.cmmsApi.service;

import jakarta.servlet.http.HttpServletResponse;
import pl.medos.cmmsApi.model.Machine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ExportService {

    void excelModelGenerator(List<Machine> machines);

    void generateExcelFile(HttpServletResponse response) throws IOException;


}
