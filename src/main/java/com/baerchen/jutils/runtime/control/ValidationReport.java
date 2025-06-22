package com.baerchen.jutils.runtime.control;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidationReport<T> {
    private boolean valid;
    private String cause;
    private String data;

    public void throwIfInvalid() {
        if (!valid) {
            throw new IllegalStateException("Validation failed: " + cause);
        }
    }
}