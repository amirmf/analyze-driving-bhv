package com.taraan.dum.service.rest;


import com.taraan.dum.common.TokenUtils;
import com.taraan.dum.dto.statistic.HomeDataDto;
import com.taraan.dum.dto.statistic.RewardDto;
import com.taraan.dum.dto.statistic.RewardPage;
import com.taraan.dum.dto.statistic.StatisticDto;
import com.taraan.dum.service.assembler.StatisticAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/statistic")
@Component
public class RestStatisticService {
    @Autowired
    private StatisticAssembler statisticAssembler;

    @GET
    @Path("/home")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHomeData(@Context HttpHeaders headers) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        HomeDataDto homeDataDto = statisticAssembler.getHomeDate(userId);
        return Response.ok().entity(homeDataDto).build();
    }

    @GET
    @Path("/rewards")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRewards(@Context HttpHeaders headers) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        List<RewardDto> rewardDtos = statisticAssembler.getRewards(userId);
        return Response.ok().entity(new RewardPage(rewardDtos)).build();
    }

    @GET
    @Path("/range/{fromDate}/{toDate}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatistics(@Context HttpHeaders headers, @PathParam("fromDate") String from,@PathParam("toDate") String to) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        StatisticDto statisticDto = statisticAssembler.getStatistics(userId,from,to);
        return Response.ok().entity(statisticDto).build();
    }

}
