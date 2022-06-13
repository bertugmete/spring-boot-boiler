package com.mkk.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionConstants {
    public static final String EXCEPTION_TYPE_BUSINESS = "BUSINESS";
    public static final String EXCEPTION_TYPE_BUSINESS_RUNTIME = "BUSINESS_RUNTIME";
    public static final String EXCEPTION_TYPE_VALIDATION = "VALIDATION";
    public static final String EXCEPTION_TYPE_RUNTIME = "RUNTIME";

    public static final boolean STATUS_FAILURE = false;

}
