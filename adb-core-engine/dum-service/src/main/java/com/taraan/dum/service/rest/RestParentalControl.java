package com.taraan.dum.service.rest;

import com.taraan.dum.common.TokenUtils;
import com.taraan.dum.dto.parentalcontrol.ParentalControlPage;
import com.taraan.dum.service.assembler.ParentalControlAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created on 7/6/2018 10:45 AM.
 * User: Reza
 * Project : driving-usage-miner
 */
@Path("/parentalControl")
@Component
public class RestParentalControl {

    @Autowired
    private ParentalControlAssembler parentalControlAssembler;

    @Path("/{phoneNumber}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createParentalControl(@Context HttpHeaders headers, @PathParam(value = "phoneNumber") String phoneNumber) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        parentalControlAssembler.createParentalControl(userId, phoneNumber);
        return Response.ok().build();
    }

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeParentalControl(@Context HttpHeaders headers, @PathParam(value = "id") Long id) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        this.parentalControlAssembler.removeParentOfParentalControl(id, userId);
        return Response.ok().build();
    }

    @Path("/child")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getChildParentalControl(@Context HttpHeaders headers) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        ParentalControlPage childOfParentalControl = parentalControlAssembler.getChildOfParentalControl(userId);
        return Response.ok().entity(childOfParentalControl).build();
    }

    @Path("/parent")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getParentParentalControl(@Context HttpHeaders headers) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        ParentalControlPage parentalControlPage = parentalControlAssembler.getParentOfParentalControl(userId);
        return Response.ok().entity(parentalControlPage).build();
    }

}
