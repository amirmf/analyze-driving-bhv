package com.taraan.dum.service.rest;

import com.taraan.dum.common.TokenUtils;
import com.taraan.dum.dto.offer.OfferDto;
import com.taraan.dum.service.assembler.OfferAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Path("/offer")
@Component
public class RestOfferService {
    @Autowired
    private OfferAssembler offerAssembler;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@Context HttpHeaders headers, @PathParam("id") Long id) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        return Response.ok().entity(offerAssembler.getOffer(id)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOffer(@Context HttpHeaders headers, OfferDto offerDto) throws IOException {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        return Response.ok().entity(offerAssembler.createOffer(offerDto, userId)).build();
    }

    @GET
    @Path("/list/{pageNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOffer(@Context HttpHeaders headers, @PathParam("pageNumber") Long page) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        return Response.ok().entity(offerAssembler.getOffers(userId, page)).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeOffer(@Context HttpHeaders headers, @PathParam("id") Long id) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        offerAssembler.removeOffer(id);
        return Response.ok().build();
    }
}
