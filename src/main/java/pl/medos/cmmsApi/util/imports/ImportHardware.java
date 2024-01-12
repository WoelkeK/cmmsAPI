package pl.medos.cmmsApi.util.imports;

import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.model.Hardware;
import pl.medos.cmmsApi.model.Job;

import java.io.IOException;
import java.util.List;

public interface ImportHardware {

    List<Hardware> importHardware(MultipartFile file) throws IOException;

}
