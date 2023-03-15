package pl.medos.cmmsApi.service;

import pl.medos.cmmsApi.model.Employee;
import pl.medos.cmmsApi.model.Person;

import java.io.IOException;
import java.util.List;

public interface ImportService {
    List<Employee> importExcelData() throws IOException;

}
