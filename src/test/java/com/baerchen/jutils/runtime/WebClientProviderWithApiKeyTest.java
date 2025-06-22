import com.baerchen.jutils.runtime.control.ValidationReport;
import com.baerchen.jutils.runtime.control.WebClientProviderWithApiKey;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WebClientProviderWithApiKeyTest {

    static class DummyProvider extends WebClientProviderWithApiKey {
        DummyProvider(Map<String, String> creds, List<String> keys) {
            super(creds, keys);
        }
    }

    @Test
    void validateReturnsInvalidWhenKeysMissing() {
        DummyProvider provider = new DummyProvider(Map.of("a", "1"), List.of("a", "b"));
        ValidationReport<Map<String, String>> report = provider.validate();
        assertFalse(report.isValid());
        assertTrue(report.getCause().contains("b"));
    }

    @Test
    void validateReturnsValidWhenAllKeysPresent() {
        DummyProvider provider = new DummyProvider(Map.of("a", "1", "b", "2"), List.of("a", "b"));
        ValidationReport<Map<String, String>> report = provider.validate();
        assertTrue(report.isValid());
        assertNull(report.getCause());
    }
}
