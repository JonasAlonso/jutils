import com.baerchen.jutils.runtime.boundary.ApiServiceImpl;
import com.baerchen.jutils.runtime.control.ApiQueryParams;
import com.baerchen.jutils.runtime.entity.ApiCallResult;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class ApiServiceImplTest {

    @Test
    void executeGetRequestReturnsInvalidWhenParamsInvalid() throws Exception {
        ApiServiceImpl service = new ApiServiceImpl();
        ApiQueryParams params = ApiQueryParams.builder()
                .params(Map.of())
                .requiredKeys(List.of("a"))
                .build();

        WebClient client = WebClient.builder().exchangeFunction(r -> Mono.empty()).build();

        ApiCallResult<String> result = service.executeGetRequest("step", "http://example.com", params, client, String.class, true);
        assertFalse(result.getValidationReport().isValid());
        assertNull(result.getResponseEntity().getBody());
    }

    @Test
    void executeGetRequestAppendsQuery() throws Exception {
        AtomicReference<URI> captured = new AtomicReference<>();
        ExchangeFunction function = request -> {
            captured.set(request.url());
            ClientResponse response = ClientResponse.create(HttpStatus.OK)
                    .header("Content-Type", "text/plain;charset=UTF-8")
                    .body("ok")
                    .build();
            return Mono.just(response);
        };
        WebClient client = WebClient.builder().exchangeFunction(function).build();

        ApiServiceImpl service = new ApiServiceImpl();
        ApiQueryParams params = ApiQueryParams.builder()
                .params(Map.of("a", "1"))
                .requiredKeys(List.of("a"))
                .build();
        ApiCallResult<String> result = service.executeGetRequest("s", "http://e.com/api", params, client, String.class, true);
        assertEquals("http://e.com/api?a=1", captured.get().toString());
        assertEquals("ok", result.getResponseEntity().getBody());
        assertTrue(result.getValidationReport().isValid());
    }
}
