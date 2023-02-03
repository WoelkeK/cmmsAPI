package pl.medos.cmmsApi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class ApiTestController {
    private static final Logger LOGGER = Logger.getLogger(ApiTestController.class.getName());

    @GetMapping("/test")
    public String apiTest() {
        LOGGER.info("test()");
        return "test ok! :Response 200";
    }
}
