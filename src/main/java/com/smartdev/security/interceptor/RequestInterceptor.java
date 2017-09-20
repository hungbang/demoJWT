package com.smartdev.security.interceptor;

import com.smartdev.security.service.CarService;
import com.smartdev.security.service.JWEServiceHandler;
import com.smartdev.security.service.JWSServiceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        System.out.println("____________________________________________");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("_________________________________________");
        System.out.println("In postHandle request processing "
                + "completed by @RestController");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("________________________________________");
        System.out.println("In afterCompletion Request Completed");
        System.out.println("________________________________________");
    }
}
