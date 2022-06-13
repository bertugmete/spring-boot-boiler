package com.mkk.exception;

import com.mkk.dto.applicationmessage.ApplicationMessageDto;
import com.mkk.enums.ValidationExceptionEnum;
import com.mkk.service.applicationmessage.ApplicationMessageService;
import com.mkk.util.web.BaseResponse;
import com.mkk.util.web.ValidationInfo;
import com.mkk.util.web.exception.ExceptionInfo;
import com.mkk.util.web.exception.RunTimeBusinessException;
import com.mkk.util.web.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;


@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ApplicationMessageService applicationMessageService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RunTimeBusinessException.class)
    public BaseResponse handleException(RunTimeBusinessException exception) {
        final ExceptionInfo exceptionInfo = this.getExceptionInfo(exception, ExceptionConstants.EXCEPTION_TYPE_BUSINESS);
        return new BaseResponse(ExceptionConstants.STATUS_FAILURE, exceptionInfo);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public BaseResponse handleException(ValidationException exception) {

        final ExceptionInfo exceptionInfo = this.getExceptionInfo(exception, ExceptionConstants.EXCEPTION_TYPE_VALIDATION);
        exceptionInfo.setErrorCode(ValidationExceptionEnum.VALIDATION_ERROR.getKey());
        exceptionInfo.setMessage(ValidationExceptionEnum.VALIDATION_ERROR.getMessage());
        exceptionInfo.setDetailedMessage(ValidationExceptionEnum.VALIDATION_ERROR.getMessage());
        BaseResponse response = new BaseResponse(ExceptionConstants.STATUS_FAILURE, exceptionInfo);
        response.setValidations(exception.getErrorInfos().stream().map(x -> ValidationInfo.builder().type(x.getType()).message(x.getMessage()).build()).collect(Collectors.toList()));

        return response;
    }

    private ExceptionInfo getExceptionInfo(Exception exception, String exceptionType) {
        ExceptionInfo exceptionInfo;

        ApplicationMessageDto applicationMessageDto = this.searchFromDatabaseMessage(exception);

        if (null != applicationMessageDto) {
            exceptionInfo = this.prepareExceptionMessage(applicationMessageDto, exceptionType);
        } else {
            exceptionInfo = this.prepareGeneralExceptionMessage(exception, exceptionType);
        }

        return exceptionInfo;
    }

    private ExceptionInfo prepareExceptionMessage(ApplicationMessageDto applicationMessageDto, String exceptionType) {

        ExceptionInfo exceptionInfo = new ExceptionInfo();
        exceptionInfo.setDetailedMessage(applicationMessageDto.getMessage());
        exceptionInfo.setMessageType(applicationMessageDto.getType().name());
        exceptionInfo.setErrorCode(applicationMessageDto.getCode());
        exceptionInfo.setMessage(applicationMessageDto.getName());
        exceptionInfo.setType(exceptionType);

        return exceptionInfo;
    }

    private ExceptionInfo prepareGeneralExceptionMessage(Exception exception, String exceptionType) {
        ExceptionInfo exceptionInfo = new ExceptionInfo();
        exceptionInfo.setMessage(exception.getMessage());
        exceptionInfo.setType(exceptionType);

        return exceptionInfo;
    }


    private ApplicationMessageDto searchFromDatabaseMessage(Exception exception) {
        final String exClassName = ClassUtils.getShortName(exception.getClass());

        ApplicationMessageDto msg = null;

        try {
            // Call the database service for exception message
            msg = applicationMessageService.findApplicationMessageByName(exClassName);

        } catch (Exception e) {
            /*
             * There is no need to throw the exception again in order to prevent exception cycle. Because this is the
             * exception handler class and the exception is already thrown. If the exception will be thrown from here
             * it is going to be intercepted from this class again.
             */
        }

        return msg;
    }
}
