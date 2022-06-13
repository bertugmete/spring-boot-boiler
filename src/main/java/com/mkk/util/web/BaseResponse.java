package com.mkk.util.web;

import com.mkk.util.web.exception.ExceptionInfo;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class BaseResponse<T> {
    private T data;
    private boolean success = true;
    private ExceptionInfo error;

    private List<ValidationInfo> validations;


    public BaseResponse(T data) {
        this.data = data;
    }

    public BaseResponse(T data, boolean success) {
        this.data = data;
        this.success = success;
    }

    public BaseResponse() {
    }

    public BaseResponse(boolean success, ExceptionInfo error) {
        this.success = success;
        this.error = error;
    }

    public BaseResponse(boolean success, ExceptionInfo error, T data) {
        this.data = data;
        this.success = success;
        this.error = error;
    }

}
