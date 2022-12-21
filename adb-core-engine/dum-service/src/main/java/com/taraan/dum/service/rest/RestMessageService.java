package com.taraan.dum.service.rest;

import com.taraan.dum.common.TokenUtils;
import com.taraan.dum.service.assembler.MessageAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/message")
public class RestMessageService {

    @Autowired
    private MessageAssembler messageAssembler;
    @GET
    @Path("/list/{pageNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessages(@Context HttpHeaders headers, @PathParam("pageNumber") Long page) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        return Response.ok().entity(messageAssembler.getMessages(userId, page)).build();
    }

    @GET
    @Path("/unreadMessageCount")
    @Produces(MediaType.APPLICATION_JSON)
    public Response unreadMessageCount(@Context HttpHeaders headers) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        return Response.ok().entity(messageAssembler.getUnreadMessageCount(userId)).build();
    }

    @POST
    @Path("/read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@Context HttpHeaders headers) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        messageAssembler.readMessages(userId);
        return Response.ok().build();
    }
}
