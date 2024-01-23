package pl.medos.cmmsApi.service;

import jakarta.servlet.http.HttpServletResponse;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.model.Hardware;
import pl.medos.cmmsApi.model.Job;
import pl.medos.cmmsApi.model.Machine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ExportService {

    void excelMachineModelGenerator(List<Machine> machines);
    void generateExcelFile(HttpServletResponse response) throws IOException;

    void excelHardwaresModelGenerator(List<Hardware> machines);
    void generateExcelHardwareFile(HttpServletResponse response) throws IOException;

    void excelJobsModelGenerator(List<Job> jobs);

    void generateExcelJobFile(HttpServletResponse response) throws IOException;

    void excelEmployeeModelGenerator(List<Employee> employees);

    void generateExcelEmployeeFile(HttpServletResponse response) throws IOException;
}

