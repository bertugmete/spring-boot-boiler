package com.mkk.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionTypeEnum {
    EXCEPTION_TYPE_BUSINESS("Business Exception"),
    EXCEPTION_TYPE_VALIDATION("VALIDATION");

    private String value;

    public String getValue(String value) {
        for (ExceptionTypeEnum exceptionTypeEnum : ExceptionTypeEnum.values()) {
            if (exceptionTypeEnum.name().equals(value)) {
                return exceptionTypeEnum.getValue();
            }
        }
        return null;
    }

}
