package com.taraan.dum.service.rest;


import com.taraan.dum.common.TokenUtils;
import com.taraan.dum.dto.trip.*;
import com.taraan.dum.service.assembler.TripAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/trip")
@Component
public class RestTripService {

    @Autowired
    private TripAssembler tripAssembler;

    @GET
    @Path("/list/{pageNumber}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getList(@Context HttpHeaders headers, @PathParam("pageNumber") Integer pageNumber) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        TripPage tripPage = tripAssembler.getTrips(userId, pageNumber);
        return Response.ok().entity(tripPage).build();
    }

    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@Context HttpHeaders headers, @PathParam("id") Long id) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        TripDto tripPage = tripAssembler.getTrip(userId, id);
        return Response.ok().entity(tripPage).build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeTrip(@Context HttpHeaders headers, @PathParam("id") Long id) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        tripAssembler.removeTrip(userId, id);
        return Response.ok().build();
    }

    @GET
    @Path("/state/active")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getState(@Context HttpHeaders headers) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        System.out.println("start active Trip. userId : " + userId);
        Boolean active = tripAssembler.isActiveTrip(userId);
        System.out.println("end active Trip. userId : " + userId + " result : " + active);
        return Response.ok().entity(new ActiveTrip(active)).build();
    }

    @GET
    @Path("/geoLocations/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGeoLocations(@Context HttpHeaders headers, @PathParam("id") Long id) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        List<TripGeoLocation> tripPage = tripAssembler.getTripGeoLocations(userId, id);
        return Response.ok().entity(new TripGeoLocationList(tripPage)).build();
    }

    @POST
    @Path("/startTrip")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response startTrip(@Context HttpHeaders headers, StartTripDto startTripDto) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        System.out.println("end active Trip. userId : " + userId);
        Long id = tripAssembler.startTrip(startTripDto, userId);
        System.out.println("end active Trip. userId : " + userId + "result : " + id);
        return Response.ok().entity(id).build();
    }

    @POST
    @Path("/endTrip")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response endTrip(@Context HttpHeaders headers, EndTripDto endTripDto) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        endTripDto.setUserId(userId);
        final boolean endTrip = tripAssembler.endTrip(endTripDto);
        if (endTrip)
            return Response.ok().build();
        else
            return Response.status(405).build();
    }

}
