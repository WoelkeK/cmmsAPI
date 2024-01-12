package pl.medos.cmmsApi.util.imports;

import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.model.Machine;

import java.io.IOException;
import java.util.List;

public interface ImportMachine {
    List<Machine> importExcelMachineData(MultipartFile fileName) throws IOException;
}
