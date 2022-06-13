package com.mkk.aop;

import com.mkk.annotation.MKKValidator;
import com.mkk.exception.BadRequestValidationException;
import com.mkk.exception.OkRequestValidationException;
import com.mkk.util.web.exception.ErrorInfo;
import com.mkk.util.web.exception.ValidationException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.ws.rs.core.Response;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Aspect
public class ValidationAspect {
    @Autowired
    private ApplicationContext applicationContext;

    public ValidationAspect() {
    }

    @Around("@annotation(com.mkk.annotation.MKKValidator)")
    public Object validate(ProceedingJoinPoint call) throws Throwable {
        Class controllerObject = call.getTarget().getClass();
        Logger logger = LoggerFactory.getLogger(controllerObject);
        MethodSignature methodSignature = (MethodSignature)call.getSignature();
        methodSignature.getDeclaringType();
        methodSignature.getParameterNames();
        Method method = controllerObject.getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        MKKValidator validatorMain = this.readAnnotation(method);
        if (validatorMain == null) {
            return null;
        } else {
            Class validatorClass = validatorMain.validator();
            if (validatorClass != null) {
                Validator validator = (Validator)this.applicationContext.getBean(validatorClass);
                if (validator.supports(controllerObject)) {
                    Map<String, Object> parameterNameValueMap = this.getNameValuePairs(call);
                    MapBindingResult mapBindingResult = new MapBindingResult(parameterNameValueMap, call.getTarget().getClass().getName());
                    ValidationUtils.invokeValidator(validator, call.getTarget(), mapBindingResult);
                    this.checkValidationException(validatorMain, mapBindingResult);
                } else {
                    String var10001 = validator.getClass().getName();
                    logger.info("Validator : " + var10001 + " is not supported for : " + controllerObject.getName());
                }
            }

            return call.proceed();
        }
    }

    private void checkValidationException(MKKValidator validatorMain, MapBindingResult mapBindingResult) {
        if (mapBindingResult.hasErrors()) {
            List errorList;
            if (validatorMain.status() == Response.Status.INTERNAL_SERVER_ERROR) {
                errorList = (List)mapBindingResult.getAllErrors().stream().map((x) -> {
                    return ErrorInfo.builder().type(x.getCode()).message(x.getDefaultMessage()).build();
                }).collect(Collectors.toList());
                ValidationException ex = new ValidationException(errorList);
                throw ex;
            } else if (validatorMain.status() == Response.Status.BAD_REQUEST) {
                errorList = (List)mapBindingResult.getAllErrors().stream().map((x) -> {
                    return ErrorInfo.builder().type(x.getCode()).message(x.getDefaultMessage()).build();
                }).collect(Collectors.toList());
                BadRequestValidationException ex = new BadRequestValidationException(errorList);
                throw ex;
            } else if (validatorMain.status() == Response.Status.OK) {
                errorList = (List)mapBindingResult.getAllErrors().stream().map((x) -> {
                    return ErrorInfo.builder().type(x.getCode()).message(x.getDefaultMessage()).build();
                }).collect(Collectors.toList());
                OkRequestValidationException ex = new OkRequestValidationException(errorList);
                throw ex;
            }
        }
    }

    private MKKValidator readAnnotation(AnnotatedElement element) {
        MKKValidator validator = null;

        try {
            Annotation[] classAnnotations = element.getAnnotations();
            Annotation[] var4 = classAnnotations;
            int var5 = classAnnotations.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Annotation annotation = var4[var6];
                if (annotation instanceof MKKValidator) {
                    validator = (MKKValidator)annotation;
                }
            }
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        return validator;
    }

    private Map<String, Object> getNameValuePairs(JoinPoint joinPoint) {
        Map<String, Object> arguments = new HashMap();
        CodeSignature codeSignature = (CodeSignature)joinPoint.getSignature();
        String[] names = codeSignature.getParameterNames();
        Object[] values = joinPoint.getArgs();

        for(int i = 0; i < values.length; ++i) {
            arguments.put(names[i], values[i]);
        }

        return arguments;
    }
}
