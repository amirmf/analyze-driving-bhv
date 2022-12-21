package com.taraan.dum.service.rest.config;

import com.taraan.dum.service.rest.*;
import com.taraan.dum.service.rest.exceptions.*;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfig extends ResourceConfig {
    public ApplicationConfig() {
        register(CORSResponseFilter.class);
        register(RestAnalyzeFormulaService.class);
        register(RestOfferService.class);
        register(RestBadgeService.class);
        register(RestChallengeService.class);
        register(RestGroupService.class);
        register(RestMessageService.class);
        register(RestNewsService.class);
        register(RestReminderService.class);
        register(RestRewardService.class);
        register(RestSosRequestService.class);
        register(RestStatisticService.class);
        register(RestTripService.class);
        register(RestTripTrackerInfoService.class);
        register(RestUserService.class);
        register(RestParentalControl.class);
        register(EntityDoesNotExistExceptionMapper.class);
        register(GenericExceptionMapper.class);
        register(InsufficientBadgeExceptionMapper.class);
        register(InvalidTokenExceptionMapper.class);
        register(InvalidUserPasswordExceptionMapper.class);
        register(UserAlreadyJoinExceptionMapper.class);
    }
}
