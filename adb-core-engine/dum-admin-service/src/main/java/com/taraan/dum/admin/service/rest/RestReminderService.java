package com.taraan.dum.admin.service.rest;


import com.taraan.dum.common.TokenUtils;
import com.taraan.dum.dto.reminder.ReminderDto;
import com.taraan.dum.dto.reminder.ReminderPage;
import com.taraan.dum.service.assembler.ReminderAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/reminder")
@Component
public class RestReminderService {

    @Autowired
    private ReminderAssembler reminderAssembler;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@Context HttpHeaders headers, @QueryParam("phoneNumber") String phoneNumber,
                        @QueryParam("fromExpirationDate") String fromExpirationDate,
                        @QueryParam("toExpirationDate") String toExpirationDate,
                        @QueryParam("fromRegisterDate") String fromRegisterDate,
                        @QueryParam("toRegisterDate") String toRegisterDate,
                        @QueryParam("from") Long from,
                        @QueryParam("size") Long size) {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        final ReminderPage reminders = reminderAssembler.getReminders(phoneNumber, fromExpirationDate, toExpirationDate, fromRegisterDate, toRegisterDate, from, size);
        return Response.ok().entity(reminders).build();
    }


}
