import com.baerchen.jutils.runtime.control.ValidationReport;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidationReportTest {

    @Test
    void throwIfInvalidThrows() {
        ValidationReport<Object> report = ValidationReport.builder()
                .valid(false)
                .cause("bad")
                .data("d")
                .build();
        assertThrows(IllegalStateException.class, report::throwIfInvalid);
    }

    @Test
    void throwIfInvalidDoesNothingWhenValid() {
        ValidationReport<Object> report = ValidationReport.builder()
                .valid(true)
                .data("d")
                .build();
        assertDoesNotThrow(report::throwIfInvalid);
    }
}
