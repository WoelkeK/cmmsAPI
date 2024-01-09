package pl.medos.cmmsApi.service;

import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.model.*;

import java.io.IOException;
import java.util.List;

public interface ImportService {
    List<Employee> importExcelEmployeesData(MultipartFile file) throws IOException;
    List<Department> importExcelDepartmentsData(MultipartFile file) throws IOException;
    List<Machine> importExcelMachineData(MultipartFile file) throws IOException;

    List<Hardware> importExcelHardwareData(MultipartFile file) throws IOException;

    List<Job> importExcelJobData(MultipartFile file) throws IOException;
}
