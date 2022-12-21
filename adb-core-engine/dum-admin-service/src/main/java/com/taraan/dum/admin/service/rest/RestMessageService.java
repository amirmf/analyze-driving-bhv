package com.taraan.dum.admin.service.rest;

import com.taraan.dum.dto.message.AdminMessagePage;
import com.taraan.dum.dto.message.MessagePage;
import com.taraan.dum.dto.message.SendMessageRequest;
import com.taraan.dum.service.assembler.MessageAssembler;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created on 7/4/2018 10:04 PM.
 * User: Reza
 * Project : driving-usage-miner
 */
@Path("/message")
public class RestMessageService {
    @Autowired
    private MessageAssembler messageAssembler;

    @Path("/send")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendMessage(SendMessageRequest message) {
        messageAssembler.sendMessage(message);
        return Response.ok().build();
    }

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getMessages(@QueryParam("phoneNumber") String phoneNumber,
                                @QueryParam("fromDate") String fromDate,
                                @QueryParam("toDate") String toDate,
                                @QueryParam("from") Long from,
                                @QueryParam("size") Long size) {
        AdminMessagePage messages = messageAssembler.getMessages(phoneNumber, fromDate, toDate, from, size);
        return Response.ok().entity(messages).build();
    }


}
