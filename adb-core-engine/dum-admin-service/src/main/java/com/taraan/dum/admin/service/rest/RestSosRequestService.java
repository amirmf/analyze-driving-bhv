package com.taraan.dum.admin.service.rest;

import com.taraan.dum.common.TokenUtils;
import com.taraan.dum.dto.sosRequest.SosRequestDto;
import com.taraan.dum.dto.sosRequest.SosRequestResponse;
import com.taraan.dum.model.hibernate.SosRequestState;
import com.taraan.dum.service.assembler.SosRequestAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/sosRequest")
public class RestSosRequestService {
    @Autowired
    private SosRequestAssembler sosRequestAssembler;

    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@Context HttpHeaders headers, @PathParam("id") Long id) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        return Response.ok().entity(sosRequestAssembler.getSosRequest(id, userId)).build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(@Context HttpHeaders headers, @PathParam("id") Long id) {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        sosRequestAssembler.removeSosRequest(id);
        return Response.ok().build();
    }

    @POST
    @Path("/approve/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response approve(@Context HttpHeaders headers, @PathParam("id") Long id) {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        sosRequestAssembler.changeSosRequestState(id, SosRequestState.APPROVE);
        return Response.ok().build();
    }

    @POST
    @Path("/reject/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response reject(@Context HttpHeaders headers, @PathParam("id") Long id) {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        sosRequestAssembler.changeSosRequestState(id, SosRequestState.REJECT);
        return Response.ok().build();
    }


    @GET
    @Path("/list}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response gets(@Context HttpHeaders headers,
                         @QueryParam("phoneNumber") String phoneNumber,
                         @QueryParam("state") String state,
                         @QueryParam("from") Long from,
                         @QueryParam("size") Long size) {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        return Response.ok().entity(sosRequestAssembler.getSosRequests(phoneNumber, state, from, size)).build();
    }

}
