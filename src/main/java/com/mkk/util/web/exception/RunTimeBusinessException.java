package com.mkk.util.web.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class RunTimeBusinessException extends RuntimeException implements HasParametricExceptionMessage {
    private String code = "";
    private String[] messageParams;

    public RunTimeBusinessException() {
    }

    public RunTimeBusinessException(String... messageParams) {
        this.messageParams = messageParams;
    }

    public RunTimeBusinessException messageParams(String... messageParams) {
        this.messageParams = messageParams;
        return this;
    }

    public RunTimeBusinessException(String message) {
        super(message);
    }

    public RunTimeBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public RunTimeBusinessException(String code, String message) {
        super(message);
        this.code = code;
    }
    
    @Override
    public String[] getMessageParams() {
        return new String[0];
    }

    @Override
    public void setMessageParams(String... var1) {

    }
}
