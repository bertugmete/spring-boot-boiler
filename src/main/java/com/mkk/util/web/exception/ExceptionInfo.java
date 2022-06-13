package com.mkk.util.web.exception;

import lombok.Data;

@Data
public class ExceptionInfo {
    private String errorCode;
    private String message;
    private String messageType;
    private String detailedMessage;
    private String errorPage;
    private String type;
    private String sourceSystem;

    public String toString() {
        return "ExceptionInfo{errorCode='" + this.errorCode + "', message='" + this.message + "', messageType='" + this.messageType + "', detailedMessage='" + this.detailedMessage + "', errorPage='" + this.errorPage + "', type='" + this.type + "', sourceSystem='" + this.sourceSystem + "'}";
    }

}
