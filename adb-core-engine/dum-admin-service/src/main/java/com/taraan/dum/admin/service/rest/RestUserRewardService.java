package com.taraan.dum.admin.service.rest;


import com.taraan.dum.dto.reward.AdminUserRewardPage;
import com.taraan.dum.dto.trip.TripPage;
import com.taraan.dum.service.assembler.RewardAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/trip")
@Component
public class RestUserRewardService {
    @Autowired
    private RewardAssembler rewardAssembler;

    @GET
    @Path("/list")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@Context HttpHeaders headers,
                        @QueryParam("phoneNumber") String phoneNumber,
                        @QueryParam("fromDate") String fromDate,
                        @QueryParam("toDate") String toDate,
                        @QueryParam("rewardId") Long rewardId,
                        @QueryParam("userRewardType") String userRewardType,
                        @QueryParam("from") Long from,
                        @QueryParam("size") Long size) {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        AdminUserRewardPage adminUserRewardPage = rewardAssembler.getRewards(phoneNumber, fromDate, toDate, userRewardType, from, size);
        return Response.ok().entity(adminUserRewardPage).build();
    }

}
