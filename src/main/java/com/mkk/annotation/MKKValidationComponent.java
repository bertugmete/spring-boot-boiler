package com.mkk.annotation;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Component
@Scope(
        scopeName = "singleton",
        proxyMode = ScopedProxyMode.TARGET_CLASS
)
public @interface MKKValidationComponent {
}
