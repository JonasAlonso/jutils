package com.baerchen.jutils.runtime.boundary;

public interface RequestBuilder <I, O>{
    O build(I input);
}
