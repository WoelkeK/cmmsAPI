package pl.medos.cmmsApi.service;

import pl.medos.cmmsApi.model.Department;
import pl.medos.cmmsApi.model.Employee;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ImportService {
    List<Employee> importExcelEmployeesData(String fileName) throws IOException;
    List<Department> importExcelDepartmentsData(String fileName) throws IOException;


}
