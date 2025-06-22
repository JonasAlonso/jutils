import com.baerchen.jutils.runtime.control.ValidationReport;
import com.baerchen.jutils.runtime.entity.ApiCredentials;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

class ApiCredentialsTest {

    static class DummyCreds extends ApiCredentials {
        DummyCreds(Map<String,String> creds, List<String> keys) {
            super(creds, keys);
        }

        @Override
        public ValidationReport<Map<String, String>> validate() {
            return ValidationReport.<Map<String, String>>builder()
                    .valid(containsAllKeys(getKeys()))
                    .cause("validation")
                    .data(getCredentials().toString())
                    .build();
        }
    }

    @Test
    void containsAllAnyAndMissing() {
        Map<String,String> map = Map.of("a","1", "b", null);
        DummyCreds creds = new DummyCreds(map, List.of("a","b","c"));

        assertFalse(creds.containsAllKeys(List.of("a","b")));
        assertTrue(creds.containsAnyKey(List.of("b","c")));
        List<String> missing = creds.missingKeys(List.of("b","c"));
        assertEquals(List.of("b","c"), missing);
    }
}
