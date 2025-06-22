package com.baerchen.jutils.runtime.control;

import com.baerchen.jutils.runtime.entity.JUtilsException;

@FunctionalInterface
public interface ResponseValidator<T> {
    void validate(T response) throws JUtilsException;
}
