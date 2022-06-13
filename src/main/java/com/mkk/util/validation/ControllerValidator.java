package com.mkk.util.validation;

import com.mkk.enums.ValidationExceptionEnum;
import org.springframework.validation.MapBindingResult;

public class ControllerValidator {
    protected static final String REQ_NAME = "request";

    public static void reject(MapBindingResult result, ValidationExceptionEnum exceptionEnum) {
        result.reject(exceptionEnum.getKey(), exceptionEnum.getMessage());
    }
}
