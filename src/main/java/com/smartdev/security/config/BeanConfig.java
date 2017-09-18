package com.smartdev.security.config;

import com.smartdev.security.JWKGenerator;
import com.smartdev.security.service.JWEServiceHandler;
import com.smartdev.security.service.JWEDataHelper;
import com.smartdev.security.service.JWSServiceHandler;
import com.smartdev.security.service.RestEZServiceHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public JWKGenerator generator(){
        return new JWKGenerator();
    }

    @Bean
    public JWEDataHelper jweDataHelper(){
        return new JWEDataHelper();
    }

    @Bean
    public JWEServiceHandler joseServiceHandler(){
        return new JWEServiceHandler();
    }

    @Bean
    public JWSServiceHandler jwsServiceHandler() {
        return new JWSServiceHandler();
    }

    @Bean
    public RestEZServiceHandler restEZServiceHandler() {
        return new RestEZServiceHandler();
    }
}
