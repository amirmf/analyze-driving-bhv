package com.taraan.dum.admin.service.rest.config;

import com.taraan.dum.admin.service.rest.*;
import com.taraan.dum.admin.service.rest.exceptions.EntityDoesNotExistExceptionMapper;
import com.taraan.dum.admin.service.rest.exceptions.GenericExceptionMapper;
import com.taraan.dum.admin.service.rest.exceptions.InvalidUserPasswordExceptionMapper;
import com.taraan.dum.admin.service.rest.exceptions.NullPointerExceptionMapper;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfig extends ResourceConfig {
    public ApplicationConfig() {
        register(CORSResponseFilter.class);
        register(RestChallengeService.class);
        register(RestAccountService.class);
        register(RestAccountRoleService.class);
        register(RestAccountGroupService.class);
        register(RestUserService.class);
        register(RestNewsService.class);
        register(RestReminderService.class);
        register(RestTripService.class);
        register(RestSosRequestService.class);
        register(RestBadgeService.class);
        register(RestRewardService.class);
        register(RestMessageService.class);
        register(RestVoucherService.class);
        register(RestAnalyzeFormulaService.class);
        register(GenericExceptionMapper.class);
        register(InvalidUserPasswordExceptionMapper.class);
        register(EntityDoesNotExistExceptionMapper.class);
        register(NullPointerExceptionMapper.class);
        register(MultiPartFeature.class);
    }
}
