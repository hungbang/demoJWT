package com.smartdev.security.filter;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;

//@ControllerAdvice
public class RequestFilter implements RequestBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        System.out.println("support Request body advice");
        System.out.println("support Request body advice");
        System.out.println("support Request body advice");
        return true;
    }

    @Override
    public Object handleEmptyBody(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        System.out.println("handle Empty Body Request body advice");
        System.out.println("handle Empty Body Request body advice");
        System.out.println("handle Empty Body Request body advice");
        return null;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
        System.out.println("before Body Read Request body advice");
        System.out.println("before Body Read Request body advice");
        System.out.println("before Body Read Request body advice");
        return null;
    }

    @Override
    public Object afterBodyRead(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        System.out.println("after Body Read Request body advice");
        System.out.println("after Body Read Request body advice");
        System.out.println("after Body Read Request body advice");
        return null;
    }
}
