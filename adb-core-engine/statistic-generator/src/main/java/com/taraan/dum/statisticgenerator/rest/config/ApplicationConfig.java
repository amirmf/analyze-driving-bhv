package com.taraan.dum.statisticgenerator.rest.config;

import com.taraan.dum.statisticgenerator.rest.RestStatisticGeneratorService;
import com.taraan.dum.statisticgenerator.rest.exceptions.GenericExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig() {
        register(RestStatisticGeneratorService.class);
        register(GenericExceptionMapper.class);
    }
}
