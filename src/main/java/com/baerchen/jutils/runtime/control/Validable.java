package com.baerchen.jutils.runtime.control;

public interface Validable<T> {

    ValidationReport<T> validate();

    default void throwIfInvalid() {
        ValidationReport<T> report = validate();
        report.throwIfInvalid();
    }

}
