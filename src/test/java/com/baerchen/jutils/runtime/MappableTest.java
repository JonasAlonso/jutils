import com.baerchen.jutils.runtime.entity.Mappable;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MappableTest {
    static class BadMappable implements Mappable {
        boolean valid;
        BadMappable(boolean valid){this.valid = valid;}
        @Override
        public boolean isValidInput(){return valid;}
    }

    @Test
    void toSafeStringReturnsFallbackOnException() {
        BadMappable m = new BadMappable(true);
        String s = m.toSafeString(o -> { throw new RuntimeException("boom"); });
        assertTrue(s.contains("Unserializable"));
    }

    @Test
    void throwIfInvalidInputThrowsWhenInvalid() {
        BadMappable m = new BadMappable(false);
        assertThrows(IllegalStateException.class, m::throwIfInvalidInput);
    }
}
