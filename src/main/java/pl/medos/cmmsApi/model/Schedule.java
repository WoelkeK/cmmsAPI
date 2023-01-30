package pl.medos.cmmsApi.model;

import java.time.LocalDate;

public class Schedule {

    private Machine machine;
    private LocalDate lastService;
    private LocalDate plannedService;
    private Stock stock;
    private SparePart sparePart;
}
