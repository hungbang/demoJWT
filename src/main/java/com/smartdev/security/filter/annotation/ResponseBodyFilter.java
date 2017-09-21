package com.smartdev.security.filter.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseBodyFilter {
    // JSON keys that will be used for filtering
    String[] keys() default {};
}