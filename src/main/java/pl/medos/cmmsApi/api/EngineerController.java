package pl.medos.cmmsApi.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.EmployeeNotFoundException;
import pl.medos.cmmsApi.model.Engineer;
import pl.medos.cmmsApi.service.EngineerService;

@RestController
@RequestMapping("/api/engineers/")
@Slf4j
public class EngineerController {

    private EngineerService engineerService;

    public EngineerController(EngineerService engineerService) {
        this.engineerService = engineerService;
    }

    @PostMapping("/create")
    public Engineer createEngineer(@RequestBody Engineer engineer) {
        log.debug("createEngineer()");
        return engineerService.createEngineer(engineer);
    }

    @GetMapping("/findById")
    public Engineer findEngineerById(@PathVariable Long id) throws EmployeeNotFoundException {
        log.debug("findEngineerById({})", id);
        return engineerService.findEngineerById(id);
    }

    @PatchMapping("/update")
    public Engineer updateEngineer(@RequestBody Engineer engineer, @PathVariable Long id) throws EmployeeNotFoundException {
        log.debug("updateEngineer({})", id);
        return engineerService.updateEngineer(engineer, id);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity deleteAll() {
        log.debug("deleteAll()");
        engineerService.deleteAll();
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteById(@PathVariable Long id) {
        log.debug("deleteById({})", id);
        engineerService.deleteEngineer(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
