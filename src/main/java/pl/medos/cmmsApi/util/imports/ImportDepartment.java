package pl.medos.cmmsApi.util.imports;

import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.model.Department;

import java.io.IOException;
import java.util.List;

public interface ImportDepartment {

    List<Department> importExcelDepartmentsData(MultipartFile fileName) throws IOException;
}
