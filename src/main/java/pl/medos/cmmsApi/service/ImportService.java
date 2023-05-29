package pl.medos.cmmsApi.service;

import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.model.Hardware;
import pl.medos.cmmsApi.model.Machine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ImportService {
    List<Employee> importExcelEmployeesData(MultipartFile file) throws IOException;
    List<Department> importExcelDepartmentsData(MultipartFile file) throws IOException;
    List<Machine> importExcelMachineData(MultipartFile file) throws IOException;
    List<Hardware> importExcelHardwareeData(MultipartFile file) throws IOException;
}
