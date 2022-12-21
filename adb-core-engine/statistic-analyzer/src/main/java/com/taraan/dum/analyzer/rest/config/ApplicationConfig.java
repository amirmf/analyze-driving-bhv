package com.taraan.dum.analyzer.rest.config;

import com.taraan.dum.analyzer.rest.RestAnalyzeService;
import com.taraan.dum.analyzer.rest.exceptions.GenericExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig() {
        register(RestAnalyzeService.class);
        register(GenericExceptionMapper.class);
    }
}
