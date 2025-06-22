import com.baerchen.jutils.runtime.control.ApiQueryParams;
import com.baerchen.jutils.runtime.control.ValidationReport;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

class ApiQueryParamsTest {

    @Test
    void validateDetectsMissingRequiredParams() {
        ApiQueryParams params = ApiQueryParams.builder()
                .params(Map.of("foo", "bar"))
                .requiredKeys(List.of("foo", "bar"))
                .build();

        ValidationReport<Map<String, String>> report = params.validate();
        assertFalse(report.isValid());
        assertTrue(report.getCause().contains("bar"));
    }

    @Test
    void toQueryStringEncodesAndIgnoresNulls() {
        ApiQueryParams params = ApiQueryParams.builder()
                .params(Map.of("f o", "b a r", "baz", null))
                .build();

        String qs = params.toQueryString();
        assertEquals("f%20o=b%20a%20r", qs);
    }
}
