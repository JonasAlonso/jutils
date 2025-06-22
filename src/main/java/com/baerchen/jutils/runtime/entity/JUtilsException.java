package com.baerchen.jutils.runtime.entity;

public class JUtilsException extends RuntimeException {
    public JUtilsException(String message) {
        super(message);
    }

    public JUtilsException(String message, Throwable cause) {
        super(message, cause);
    }
}
