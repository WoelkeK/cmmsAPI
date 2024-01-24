//package pl.medos.cmmsApi.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//
//@Component
//@Slf4j
//public class Sheduler {
//
//    @Scheduled(fixedRate = 5000)
//    public void scheduleTask() {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
//
//        String strDate = dateFormat.format(new Date());
//        System.out.println("Zadanie uruchomione cyklicznie " + strDate);
//
////        jobs.stream().forEach(job -> {
////
////                    if (job.getJobStatus().equals(JobStatus.PRZEGLĄD) && (job.getStatus().equalsIgnoreCase("zakończone"))) {
////
////                        if (job.getDecision().equals(Decision.TAK)) {
////
////                            LocalDateTime futureJobDate = jobService.calculateFutureDate(job.getJobShedule(), job.getDateOffset(), job.getOffset());
////                            Job cycleJob = new Job();
////                            cycleJob.setMessage(job.getMessage());
////                            cycleJob.setMachine(job.getMachine());
////                            cycleJob.setStatus("przegląd");
////                            cycleJob.setDepartment(job.getDepartment().);
////                            cycleJob.setEmployee(job.getEmployee());
////                            cycleJob.setDecision(job.getDecision());
////                            cycleJob.setDateOffset(job.getDateOffset());
////                            cycleJob.setJobShedule(futureJobDate);
////                            cycleJob.setOffset(job.getOffset());
////                            cycleJob.setOpen(job.isOpen());
////                            cycleJob.setOriginalImage(job.getOriginalImage());
////                            cycleJob.setResizedImage(job.getResizedImage());
////                            cycleJob.setJobStatus(JobStatus.PRZEGLĄD);
////
////                            log.info("Create new cyclic job ");
////                        }
////                    }
////                }
////        );
//    }
//}
