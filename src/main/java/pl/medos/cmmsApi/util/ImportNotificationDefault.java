package pl.medos.cmmsApi.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.medos.cmmsApi.model.Notification;
import pl.medos.cmmsApi.model.NotificationJson;
import pl.medos.cmmsApi.util.imports.DateConverter;
import pl.medos.cmmsApi.util.imports.ImportNotification;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;

@Component
public class ImportNotificationDefault implements ImportNotification {


    private static final Logger LOGGER = Logger.getLogger(ImportNotificationDefault.class.getName());
    private List<String> notifications = new ArrayList<>(Arrays.asList("visitDate", "type", "status", "supplier", "item", "itemDetails", "carPlates", "driverName", "driverPhone", "employee", "employeePhone", "description"));

    @Override
    public List<Notification> importNotificationFromXLS(MultipartFile fileName) throws IOException {

        LOGGER.info("importExcelNotificationData()");
        List<NotificationJson> rawDataList = new ArrayList<>();

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
            String empty = "";
            Cell cell;
            for (int k = 0; k < row.getLastCellNum(); k++) {
                if (null != (cell = row.getCell(k))) {
                    switch (cell.getCellType()) {
                        case NUMERIC:
                            rowDataMap.put(notifications.get(k), (cell.getDateCellValue().toString()));
                            break;
                        case STRING:
                            rowDataMap.put(notifications.get(k), cell.getStringCellValue().replaceAll("  ", " ").trim());
                            break;
                        case _NONE:
                            rowDataMap.put(notifications.get(k), empty);
                            break;
                    }
                }
            }
            ObjectMapper mapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

            NotificationJson rawData = mapper.convertValue(rowDataMap, NotificationJson.class);
            rawDataList.add(rawData);
            LOGGER.info("rawData " + rawData);
        }
        List<Notification> convertedNotifications = notificationDataExcelConverter(rawDataList);
        return convertedNotifications;
    }

    private List<Notification> notificationDataExcelConverter(List<NotificationJson> notifications) {
        LOGGER.info("notificationDataExcelConverter()");

        List<Notification> convertedNotifications =
                notifications.stream().map(m -> {

                                    Notification notification = new Notification();
                                    if (m.getVisitDate() == null || m.getVisitDate().isEmpty()) {
                                        notification.setVisitDate(null);
                                    } else {
                                        LocalDateTime visitDate = DateConverter.convertDateTime(m.getVisitDate());
                                        notification.setVisitDate(visitDate);
                                    }
                                    notification.setType(m.getType());
                                    notification.setStatus(m.getStatus());
                                    notification.setSupplier(m.getSupplier());
                                    notification.setItem(m.getItem());
                                    notification.setItemDetails(m.getItemDetails());
                                    notification.setCarPlates(m.getCarPlates());
                                    notification.setDriverName(m.getDriverName());
                                    notification.setDriverPhone(m.getDriverPhone());
                                    notification.setEmployee(m.getEmployee());
                                    notification.setEmployeePhone(m.getEmployeePhone());
                                    notification.setDescription(m.getDescription());
                                    return notification;
                                }
                        )
                        .toList();
        LOGGER.info("notificationDataExcelConverter(...)");
        return convertedNotifications;
    }
}
