import com.baerchen.jutils.runtime.control.Loggable;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoggingAspectTest {

    @Autowired
    DummyComponent component;

    @Test
    void stepIsPutIntoMdcDuringCall() {
        String inside = component.run("x");
        assertEquals("x", inside);
        assertNull(MDC.get("step"));
    }

    @Component
    static class DummyComponent {
        @Loggable(logParams = false, logResult = false)
        public String run(String step) {
            return MDC.get("step");
        }
    }
}
