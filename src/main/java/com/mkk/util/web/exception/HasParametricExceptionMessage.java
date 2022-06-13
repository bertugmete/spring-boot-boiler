package com.mkk.util.web.exception;

public interface HasParametricExceptionMessage {
    String[] getMessageParams();

    void setMessageParams(String... var1);
}
