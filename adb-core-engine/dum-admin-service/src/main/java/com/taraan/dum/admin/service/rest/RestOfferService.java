package com.taraan.dum.admin.service.rest;

import com.taraan.dum.service.assembler.OfferAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/offer")
@Component
public class RestOfferService {
    @Autowired
    private OfferAssembler offerAssembler;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@Context HttpHeaders headers, @PathParam("id") Long id) {
        /*List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        */
        return Response.ok().entity(offerAssembler.getAdminOffer(id)).build();
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccount(@Context HttpHeaders headers,
                               @QueryParam("title") String title,
                               @QueryParam("phoneNumber") String phoneNumber,
                               @QueryParam("fromDate") String fromDate,
                               @QueryParam("toDate") String toDate,
                               @QueryParam("from") Long from,
                               @QueryParam("size") Long size) {
        /*List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        */
        return Response.ok().entity(offerAssembler.getOffers(title,phoneNumber,fromDate,toDate, from, size)).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeOffer(@Context HttpHeaders headers, @PathParam("id") Long id) {
        /*List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        */
        offerAssembler.removeOffer(id);
        return Response.ok().build();
    }
}
