package com.mkk.enums;

import lombok.Getter;

@Getter
public enum ValidationExceptionEnum {
    VALIDATION_ERROR("9999", "Validation error."),
    USER_NAME_CANNOT_BE_NULL("9999", "Kullanıcı adı boş olamaz.");

    private final String key;
    private final String message;

    ValidationExceptionEnum(String key, String message) {
        this.key = key;
        this.message = message;
    }
}
