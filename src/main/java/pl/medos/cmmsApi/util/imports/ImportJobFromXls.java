package pl.medos.cmmsApi.util.imports;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.model.*;
import pl.medos.cmmsApi.service.DepartmentService;
import pl.medos.cmmsApi.service.EmployeeService;
import pl.medos.cmmsApi.service.ImageService;
import pl.medos.cmmsApi.service.MachineService;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;

@Component
public class ImportJobFromXls implements ImportJob {

    private static final Logger LOGGER = Logger.getLogger(ImportJobFromXls.class.getName());
    private List<String> jobs = new ArrayList<>(Arrays.asList("requestDate", "employee", "department", "machine", "message", "solution", "jobStartTime", "jobStopTime"));

    private EmployeeService employeeService;
    private DepartmentService departmentService;
    private MachineService machineService;
    private ImageService imageService;

    public ImportJobFromXls(EmployeeService employeeService, DepartmentService departmentService, MachineService machineService, ImageService imageService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.machineService = machineService;
        this.imageService = imageService;
    }

    @Override
    public List<Job> importExcelJobData(MultipartFile fileName) throws IOException {

        LOGGER.info("importExcelJobsData()");
        String empty = "";

        List<JsonJob> rawDataList = new ArrayList<>();
        InputStream file = new BufferedInputStream(fileName.getInputStream());

        IOUtils.setByteArrayMaxOverride(Integer.MAX_VALUE);
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();

        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            if (row.getRowNum() == 0) {
                continue;
            }
            Map<String, String> rowDataMap = new HashMap<>();
            Cell cell;
            for (int k = 0; k < row.getLastCellNum(); k++) {

                if (null != (cell = row.getCell(k))) {
                    switch (cell.getCellType()) {
                        case NUMERIC:
//                            rowDataMap.put(jobs.get(k), NumberToTextConverter.toText(cell.getNumericCellValue()));
                            rowDataMap.put(jobs.get(k), (cell.getDateCellValue().toString()));
                            break;
                        case STRING:
//                            rowDataMap.put(persons.get(k), cell.getStringCellValue());
                            rowDataMap.put(jobs.get(k), cell.getStringCellValue().replaceAll("  ", "").trim());
                            break;
                        case _NONE:
                            rowDataMap.put(jobs.get(k), empty);
                            break;

                    }
                }
            }
            ObjectMapper mapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
            JsonJob rawData = mapper.convertValue(rowDataMap, JsonJob.class);
            rawDataList.add(rawData);
            LOGGER.info("rawData " + rawData);
        }
        List<Job> jobs = jobsDataExcelConverter(rawDataList);
        return jobs;
    }

    private List<Job> jobsDataExcelConverter(List<JsonJob> jobs) {
        LOGGER.info("jobDataExcelConverter()");

        List<Job> newJobs =
                jobs.stream().map(m -> {

                                    Job job = new Job();
//                                    job.setId(Long.parseLong(String.valueOf(m.getId())));

                                    Employee employee = employeeService.findEmployeeByRawName(m.getEmployee()).get(0);
                                    LOGGER.info("Imported employee() " + employee);
                                    job.setEmployee(employee);

                                    Department departmentByName = departmentService.findDepartmentByName(m.getDepartment());
                                    LOGGER.info("Imported department() " + departmentByName);
                                    job.setDepartment(departmentByName);
                                    job.setSolution(m.getSolution());

                                    Machine machine = machineService.findMachinesByQuery(m.getMachine()).stream().findFirst().orElseThrow();
                                    LOGGER.info("Imported machine() " + machine);
                                    job.setMachine(machine);
                                    job.setMessage(m.getMessage());

                                    if (job.getOriginalImage() == null) {
                                        LOGGER.info("default image");
                                        byte[] bytes = imageService.imageToByteArray();
                                        job.setResizedImage(bytes);
                                        job.setOriginalImage(bytes);
                                    }

                                    LOGGER.info("image prepared");

                                    if (m.getJobStartTime() == null || m.getJobStartTime().isEmpty()) {
                                        job.setJobStartTime(null);
                                    } else {
                                        LocalDateTime jobStartTime = DateConverter.convertDateTime(m.getJobStartTime());
                                        job.setJobStartTime(jobStartTime);
                                        LOGGER.info(jobStartTime.toString());
                                    }

                                    if (m.getJobStopTime() == null || m.getJobStopTime().isEmpty()) {
                                        job.setJobStopTime(null);
                                    } else {
                                        LocalDateTime jobStopTime = DateConverter.convertDateTime(m.getJobStopTime());
                                        job.setJobStopTime(jobStopTime);
                                        LOGGER.info(jobStopTime.toString());
                                    }

                                    if (m.getRequestDate() == null || m.getRequestDate().isEmpty()) {
                                        LOGGER.info("RequestDate null");
                                        job.setRequestDate(null);
                                    } else {
                                        LocalDateTime requestDate = DateConverter.convertDateTime(m.getRequestDate());
                                        job.setRequestDate(requestDate);
                                        LOGGER.info(requestDate.toString());
                                    }

                                    LOGGER.info("createJobMap(...)");
                                    return job;
                                }
                        )
                        .toList();

        LOGGER.info("employeeDataExcelConverter(...)");
        return newJobs;
    }
}
