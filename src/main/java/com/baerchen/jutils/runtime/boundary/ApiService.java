package com.baerchen.jutils.runtime.boundary;


import com.baerchen.jutils.runtime.control.ApiQueryParams;
import com.baerchen.jutils.runtime.entity.ApiCallResult;
import com.baerchen.jutils.runtime.entity.JUtilsException;
import com.baerchen.jutils.runtime.control.Loggable;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Defines the contract for executing HTTP requests using WebClient.
 */
public interface ApiService {

    /**
     * Executes a POST request.
     *
     * @param step         MDC step identifier.
     * @param url          Endpoint URL.
     * @param request      Serialized request body.
     * @param webClient    WebClient instance.
     * @param responseType Expected class of the response body.
     * @param expectBody   Whether a response body is expected.
     * @return ApiCallResult wrapping the response and validation.
     * @throws JUtilsException when the request fails.
     */
    @Loggable(description = "POST request to external API", logParams = true, logResult = true)
    <T> ApiCallResult<T> executePostRequest(String step, String url, String request,
                                            WebClient webClient,
                                            Class<T> responseType,
                                            boolean expectBody) throws JUtilsException;

    /**
     * Executes a GET request with query parameters.
     *
     * @param step         MDC step identifier.
     * @param baseUrl      Base endpoint URL.
     * @param queryParams  Query parameters to append.
     * @param webClient    WebClient instance.
     * @param responseType Expected class of the response body.
     * @param expectBody   Whether a response body is expected.
     * @return ApiCallResult wrapping the response and validation.
     * @throws JUtilsException when the request fails.
     */
    @Loggable(description = "GET request to external API", logParams = true, logResult = true)

    <T> ApiCallResult<T> executeGetRequest(String step,
                                           String baseUrl,
                                           ApiQueryParams queryParams,
                                           WebClient webClient,
                                           Class<T> responseType,
                                           boolean expectBody) throws JUtilsException;

    /**
     * Executes a PUT request.
     *
     * @param step         MDC step identifier.
     * @param url          Endpoint URL.
     * @param request      Serialized request body.
     * @param webClient    WebClient instance.
     * @param responseType Expected class of the response body.
     * @param expectBody   Whether a response body is expected.
     * @return ApiCallResult wrapping the response and validation.
     * @throws JUtilsException when the request fails.
     */
    @Loggable(description = "PUT request to external API", logParams = true, logResult = true)

    <T> ApiCallResult<T> executePutRequest(String step, String url, String request,
                                           WebClient webClient,
                                           Class<T> responseType,
                                           boolean expectBody) throws JUtilsException;
}
