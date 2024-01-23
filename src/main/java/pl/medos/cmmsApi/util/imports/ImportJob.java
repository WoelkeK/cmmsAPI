package pl.medos.cmmsApi.util.imports;

import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.model.Job;

import java.io.IOException;
import java.util.List;

public interface ImportJob {
    List<Job> importExcelJobData(MultipartFile fileName) throws IOException;
}
