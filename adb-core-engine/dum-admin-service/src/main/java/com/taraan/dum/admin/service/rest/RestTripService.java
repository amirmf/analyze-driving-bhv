package com.taraan.dum.admin.service.rest;


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
    @Path("/list")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@Context HttpHeaders headers,
                        @QueryParam("phoneNumber") String phoneNumber,
                        @QueryParam("fromStartDate") String fromStartDate,
                        @QueryParam("toStartDate") String toStartDate,
                        @QueryParam("fromDistance") Double fromDistance,
                        @QueryParam("toDistance") Double toDistance,
                        @QueryParam("fromDistance") Double fromDuration,
                        @QueryParam("toDuration") Double toDuration,
                        @QueryParam("from") Long from,
                        @QueryParam("size") Long size) {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        TripPage tripPage = tripAssembler.getTrips(phoneNumber, fromStartDate, toStartDate, fromDistance, toDistance, fromDuration, toDuration, from, size);
        return Response.ok().entity(tripPage).build();
    }

    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@Context HttpHeaders headers, @PathParam("id") Long id) {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        TripDto tripPage = tripAssembler.getTrip(id);
        return Response.ok().entity(tripPage).build();
    }

    @GET
    @Path("/geoLocations/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGeoLocations(@Context HttpHeaders headers, @PathParam("id") Long id) {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        List<TripGeoLocation> tripPage = tripAssembler.getTripGeoLocations(id);
        return Response.ok().entity(new TripGeoLocationList(tripPage)).build();
    }


}
