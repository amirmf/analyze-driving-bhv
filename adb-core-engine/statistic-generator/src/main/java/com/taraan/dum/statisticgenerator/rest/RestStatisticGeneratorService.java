package com.taraan.dum.statisticgenerator.rest;


import com.taraan.dum.dto.trip.CreateTripInfoRequest;
import com.taraan.dum.dto.trip.TripInfoDto;
import com.taraan.dum.statisticgenerator.assembler.StatisticGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/statisticGenerator")
@Component
public class RestStatisticGeneratorService {

    @Autowired
    private StatisticGenerator statisticGenerator;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(CreateTripInfoRequest request) {
        final TripInfoDto tripStatistic = statisticGenerator.createTripStatistic(request);
        return Response.ok().entity(tripStatistic).build();

    }

}
