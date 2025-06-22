import com.baerchen.jutils.runtime.control.WebClientErrorBuilder;
import com.baerchen.jutils.runtime.entity.JUtilsClientException;
import com.baerchen.jutils.runtime.entity.JUtilsServerException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

class WebClientErrorBuilderTest {

    @Test
    void handle4xxReturnsClientException() {
        ClientResponse response = ClientResponse.create(HttpStatus.BAD_REQUEST)
                .body("bad request")
                .header("Content-Type", "text/plain;charset=UTF-8")
                .build();

        assertThrows(JUtilsClientException.class, () ->
                WebClientErrorBuilder.handle4xx("req", true).apply(response).block());
    }

    @Test
    void handle5xxReturnsServerException() {
        ClientResponse response = ClientResponse.create(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("error")
                .header("Content-Type", "text/plain;charset=UTF-8")
                .build();

        assertThrows(JUtilsServerException.class, () ->
                WebClientErrorBuilder.handle5xx("req", true).apply(response).block());
    }
}
