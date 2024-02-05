package pl.medos.cmmsApi.service;

import jakarta.servlet.http.HttpServletResponse;
import pl.medos.cmmsApi.model.*;

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

    void excelNotificationModelGenerator(List<Notification> notifications);

    void generateExcelNotificationFile(HttpServletResponse response) throws IOException;

}

