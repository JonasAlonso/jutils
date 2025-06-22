package com.baerchen.jutils.runtime.control;

import com.baerchen.jutils.runtime.entity.JUtilsClientException;
import com.baerchen.jutils.runtime.entity.JUtilsServerException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

public class WebClientErrorBuilder {

    public static Function<ClientResponse, Mono<? extends Throwable>> handle4xx(String requestBody, boolean expectBody) {
        return response -> buildException(response, true, requestBody,expectBody);
    }

    public static Function<ClientResponse, Mono<? extends Throwable>> handle5xx(String requestBody, boolean expectBody) {
        return response -> buildException(response, false, requestBody, expectBody);
    }


    private static Mono<Throwable> buildException(ClientResponse response, boolean isClientError, String requestBody, boolean expectBody) {
            Mono<byte[]> bodyMono = expectBody
                    ? response.bodyToMono(byte[].class).defaultIfEmpty(new byte[0])
                    : Mono.just(new byte[0]);
        return bodyMono.flatMap(body -> {
            HttpStatusCode statusCode = response.statusCode();
            String statusText = statusCode.toString();
            Charset charset = response.headers().contentType()
                    .map(MediaType::getCharset)
                    .orElse(StandardCharsets.UTF_8);

            if (isClientError) {
                return Mono.error(new JUtilsClientException(statusCode, statusText, requestBody, body, charset));
            } else {
                return Mono.error(new JUtilsServerException(statusCode, statusText, requestBody, body, charset));
            }
        });
    }
}