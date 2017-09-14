package com.smartdev.security.config;

import com.smartdev.security.JWKGenerator;
import com.smartdev.security.service.JOSEServiceHandler;
import com.smartdev.security.service.JWEDataHelper;
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
    public JOSEServiceHandler joseServiceHandler(){
        return new JOSEServiceHandler();
    }
}
