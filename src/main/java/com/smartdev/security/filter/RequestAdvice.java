package com.smartdev.security.filter;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestAdvice {
    // JSON keys that will be used for filtering
    String[] keys() default {};
}