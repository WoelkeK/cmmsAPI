package pl.medos.cmmsApi.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class ApiTestController {
    private static final Logger LOGGER = Logger.getLogger(ApiTestController.class.getName());

    @GetMapping("/test")
    public TestResponse apiTest() {
        LOGGER.info("test()");
        return new TestResponse("RestApi test message");
    }

    class TestResponse {
        private final String message;
        public TestResponse(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return "TestResponse{" +
                    "message='" + message + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestResponse that = (TestResponse) o;
            return Objects.equals(message, that.message);
        }

        @Override
        public int hashCode() {
            return Objects.hash(message);
        }
    }
}
