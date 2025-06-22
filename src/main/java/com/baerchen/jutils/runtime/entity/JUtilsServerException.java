package com.baerchen.jutils.runtime.entity;

import org.springframework.http.HttpStatusCode;

import java.nio.charset.Charset;

public class JUtilsServerException extends  JUtilsException {
    private final String requestBody;

    public JUtilsServerException(HttpStatusCode status, String message, String requestBody, byte[] body, Charset charset) {
        super(String.format("Client error %s: %s", status.value(), new String(body, charset)));
        this.requestBody = requestBody;
    }

}
