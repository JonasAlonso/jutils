package com.baerchen.jutils.runtime.entity;

import com.baerchen.jutils.runtime.control.Validable;
import com.baerchen.jutils.runtime.control.ValidationReport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
@Builder
@AllArgsConstructor
public class ApiCallResult <T> implements Validable {
    private final ResponseEntity<T> responseEntity;
    private final ValidationReport<T> validationReport;

    @Override
    public ValidationReport validate() {
        return validationReport;
    }
    @Override
    public void throwIfInvalid() {
        validationReport.throwIfInvalid();
    }
}
