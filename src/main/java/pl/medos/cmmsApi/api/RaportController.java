package pl.medos.cmmsApi.api;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.medos.cmmsApi.service.ReportService;

@RestController
public class RaportController {


    @Autowired
    private ReportService reportService;

    @GetMapping("/excel")
    public void generateExcelReport(HttpServletResponse response) throws Exception{

        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=courses.xls";

        response.setHeader(headerKey, headerValue);

        reportService.generateExcel(response);

        response.flushBuffer();
    }
}
