package com.smartdev.security.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartdev.security.helper.CopyPrintWriter;
import com.smartdev.security.helper.OutputStreamResponseWrapper;
import com.smartdev.security.service.CarService;
import com.smartdev.security.service.JWEServiceHandler;
import com.smartdev.security.service.JWSServiceHandler;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponseWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

@Component
public class RequestInterceptor extends HandlerInterceptorAdapter{


    @Autowired
    CarService carService;

    @Autowired
    private JWEServiceHandler jWEServiceHandler;
    @Autowired
    private JWSServiceHandler jwsServiceHandler;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("In preHandle we are Intercepting the Request");
        System.out.println("____________________________________________");
        String requestURI = request.getRequestURI();
//        Integer personId = ServletRequestUtils.getIntParameter(request, "personId", 0);
//        System.out.println("RequestURI::" + requestURI +
//                " || Search for Person with personId ::" + personId);
        System.out.println("____________________________________________");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("_________________________________________");
        System.out.println("In postHandle request processing "
                + "completed by @RestController");
        OutputStreamResponseWrapper wrappedResponse = new OutputStreamResponseWrapper(response, ByteArrayOutputStream.class);
        ByteArrayOutputStream baos = (ByteArrayOutputStream) wrappedResponse.getRealOutputStream();
        String content = baos.toString();
        System.out.println(content);

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("________________________________________");
        System.out.println("In afterCompletion Request Completed");
        System.out.println("________________________________________");
    }
}
