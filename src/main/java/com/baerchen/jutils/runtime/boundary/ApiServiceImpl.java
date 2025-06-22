package com.baerchen.jutils.runtime.boundary;

import com.baerchen.jutils.runtime.control.ApiQueryParams;
import com.baerchen.jutils.runtime.control.ValidationReport;
import com.baerchen.jutils.runtime.control.WebClientErrorBuilder;
import com.baerchen.jutils.runtime.entity.ApiCallResult;
import com.baerchen.jutils.runtime.entity.JUtilsException;
import org.slf4j.MDC;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

public class ApiServiceImpl {

    public <T> ApiCallResult<T> executePostRequest(String step, String url, String request,
                                                   WebClient webClient,
                                                   Class<T> responseType,
                                                   boolean expectBody) throws JUtilsException {
        MDC.put("step", step);

        T res = webClient.post()
                .uri(url)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, WebClientErrorBuilder.handle4xx(request, expectBody))
                .onStatus(HttpStatusCode::is5xxServerError, WebClientErrorBuilder.handle5xx(request, expectBody))
                .bodyToMono(responseType)
                .block();

        ValidationReport<T> report = ValidationReport.<T>builder()
                .data(request)
                .valid(!expectBody || res != null)
                .cause(expectBody && res == null ? "Expecting body but got none." : null)
                .build();

        return ApiCallResult.<T>builder()
                .responseEntity(ResponseEntity.ofNullable(res))
                .validationReport(report)
                .build();
    }

    public <T> ApiCallResult<T> executeGetRequest(String step,
                                                  String baseUrl,
                                                  ApiQueryParams queryParams,
                                                  WebClient webClient,
                                                  Class<T> responseType,
                                                  boolean expectBody) throws JUtilsException {
        MDC.put("step", step);

        // ✅ Validate query parameters
        ValidationReport<Map<String, String>> queryParamReport = queryParams.validate();
        if (!queryParamReport.isValid()) {
            return ApiCallResult.<T>builder()
                    .validationReport((ValidationReport<T>) queryParamReport)
                    .responseEntity(ResponseEntity.ofNullable(null))
                    .build();
        }

        // 💡 Construct full URL with valid parameters only
        String fullUrl = baseUrl + (baseUrl.contains("?") ? "&" : "?") + queryParams.toQueryString();

        T res = webClient.get()
                .uri(fullUrl)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, WebClientErrorBuilder.handle4xx(null, expectBody))
                .onStatus(HttpStatusCode::is5xxServerError, WebClientErrorBuilder.handle5xx(null, expectBody))
                .bodyToMono(responseType)
                .block();

        ValidationReport<T> responseReport = ValidationReport.<T>builder()
                .data(queryParams.toQueryString())
                .valid(!expectBody || res != null)
                .cause(expectBody && res == null ? "Expecting body but got none." : null)
                .build();


        return ApiCallResult.<T>builder()
                .responseEntity(ResponseEntity.ofNullable(res))
                .validationReport(responseReport)
                .build();
    }

    public <T> ApiCallResult<T> executePutRequest(String step, String url, String request,
                                                  WebClient webClient,
                                                  Class<T> responseType,
                                                  boolean expectBody) throws JUtilsException {

        MDC.put("step", step);

        T res = webClient.put()
                .uri(url)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, WebClientErrorBuilder.handle4xx(request, expectBody))
                .onStatus(HttpStatusCode::is5xxServerError, WebClientErrorBuilder.handle5xx(request, expectBody))
                .bodyToMono(responseType)
                .block();

        ValidationReport<T> report = ValidationReport.<T>builder()
                .data(request)
                .valid(!expectBody || res != null)
                .cause(expectBody && res == null ? "Expecting body but got none." : null)
                .build();


        return ApiCallResult.<T>builder()
                .responseEntity(ResponseEntity.ofNullable(res))
                .validationReport(report)
                .build();
    }

}