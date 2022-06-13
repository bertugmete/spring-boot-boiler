package com.mkk.annotation;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.lang.annotation.*;


@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MKKValidator {
    Class validator();

    Response.Status status() default Status.INTERNAL_SERVER_ERROR;
}
