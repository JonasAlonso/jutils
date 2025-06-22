package com.baerchen.jutils.runtime.entity;

import org.springframework.http.HttpStatusCode;

import java.nio.charset.Charset;

public class JUtilsClientException extends JUtilsException{
    private final String requestBody;

    public JUtilsClientException(HttpStatusCode status, String message, String requestBody, byte[] body, Charset charset) {
        super(String.format("Client error %s: %s", status.value(), new String(body, charset)));
        this.requestBody = requestBody;
    }
}
