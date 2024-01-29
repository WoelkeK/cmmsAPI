package pl.medos.cmmsApi.util.imports;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.schemas.office.visio.x2012.main.CellType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.enums.DateOffset;
import pl.medos.cmmsApi.enums.Decision;
import pl.medos.cmmsApi.enums.JobStatus;
import pl.medos.cmmsApi.model.*;
import pl.medos.cmmsApi.service.*;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;

@Component
public class ImportJobFromXls implements ImportJob {

    private static final Logger LOGGER = Logger.getLogger(ImportJobFromXls.class.getName());
    private List<String> jobs = new ArrayList<>(Arrays.asList
            ("requestDate", "employee", "engineer", "department", "machine", "message", "solution", "jobStartTime",
                    "jobStopTime", "jobStatus", "jobShedule","decision","dateOffset", "status", "offset"));


//                    , "decision", "offset", "dateOffset","status"));

    private EmployeeService employeeService;
    private EngineerService engineerService;
    private DepartmentService departmentService;
    private MachineService machineService;
    private ImageService imageService;

    public ImportJobFromXls(EmployeeService employeeService, EngineerService engineerService, DepartmentService departmentService, MachineService machineService, ImageService imageService) {
        this.employeeService = employeeService;
        this.engineerService = engineerService;
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

                            if (k == row.getLastCellNum()-1) {
                                LOGGER.info("Cell Number value");
                                rowDataMap.put(jobs.get(k), NumberToTextConverter.toText(cell.getNumericCellValue()));

                            } else {
                                LOGGER.info("Cell Data value");
                                rowDataMap.put(jobs.get(k), (cell.getDateCellValue().toString()));
                            }

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

                                    Employee employee = employeeService.finadAllEmployees().get(0);
                                    LOGGER.info("Imported employee() " + employee);
                                    job.setEmployee(employee);

                                    Engineer engineer = engineerService.finadAllEngineers().get(0);
                                    LOGGER.info("Imported engineer() " + engineer);
                                    job.setEngineer(engineer);

                                    Department departmentByName = departmentService.findAllDepartments().get(0);
                                    LOGGER.info("Imported department() " + departmentByName);
                                    job.setDepartment(departmentByName);

                                    Machine machine = machineService.findMachinesByQuery(m.getMachine()).stream().findFirst().orElseThrow();
                                    LOGGER.info("Imported machine() " + machine);
                                    job.setMachine(machine);
                                    job.setMessage(m.getMessage());
                                    job.setSolution(m.getSolution());

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

                                    if (m.getJobStatus() == null) {
                                        job.setJobStatus(JobStatus.AWARIA);
                                    } else {
                                        job.setJobStatus(m.getJobStatus());
                                    }

                                    if (m.getJobShedule() == null || m.getJobShedule().isEmpty()) {
                                        job.setJobShedule(null);
                                    } else {
                                        LocalDateTime jobShedule = DateConverter.convertDateTime(m.getJobShedule());
                                        job.setJobStopTime(jobShedule);
                                        LOGGER.info(jobShedule.toString());
                                    }

                                    if (m.getDecision() == null) {
                                        job.setDecision(Decision.NIE);
                                    } else {
                                        job.setDecision(m.getDecision());
                                    }

                                    if ((m.getDateOffset() == null) || (m.getDateOffset().equals(""))) {
                                        job.setDateOffset(null);
                                    } else {
                                        job.setDateOffset(convertXLSDateOffsetField(m.getDateOffset()));
                                    }

                                    if (m.getStatus() == null) {
                                        job.setStatus("zgłoszenie");
                                    } else {
                                        job.setStatus(m.getStatus());
                                    }

                                    if(m.getOffset()==null || m.getOffset().equals("")){
                                        job.setOffset(0);
                                    }else{
                                        job.setOffset(Integer.parseInt(m.getOffset()));
                                    }

                                    LOGGER.info("Offset: " + m.getOffset());

//                                    job.setOpen(true); // TODO: 26.01.2024 napisać konwersje podobnie jak permissions
                                    LOGGER.info("createJobMap(...)");
                                    return job;
                                }
                        )

                        .toList();

        LOGGER.info("employeeDataExcelConverter(...)");
        return newJobs;
    }


    static DateOffset convertXLSDateOffsetField(String condition) {

        if (condition != null) {

            switch (condition) {
                case "DNI":
                    return DateOffset.DNI;
                case "TYGODNIE":
                    return DateOffset.TYGODNIE;
                case "MIESIĄCE":
                    return DateOffset.MIESIACE;
                case "LATA":
                    return DateOffset.LATA;
                default:
                    return DateOffset.GODZINY;
            }
        }
        return null;
    }
}
