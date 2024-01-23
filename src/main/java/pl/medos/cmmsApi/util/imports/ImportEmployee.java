package pl.medos.cmmsApi.util.imports;

import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.model.Employee;

import java.io.IOException;
import java.util.List;

public interface ImportEmployee {
    List<Employee> importExcelEmployeesData(MultipartFile fileName) throws IOException;
}
