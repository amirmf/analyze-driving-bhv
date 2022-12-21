package com.taraan.dum.service.rest;


import com.taraan.dum.common.TokenUtils;
import com.taraan.dum.dto.reward.*;
import com.taraan.dum.service.assembler.RewardAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Component
@Path("/reward")
public class RestRewardService {
    @Autowired
    public RewardAssembler rewardAssembler;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/list")
    public Response getRewards(@Context HttpHeaders headers) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        return Response.ok().entity(rewardAssembler.getRewards(userId)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/enterReward/{id}")
    public Response enterReward(@Context HttpHeaders headers, @PathParam("id") Long rewardId) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        rewardAssembler.enterRewards(userId, rewardId);
        return Response.ok().build();
    }
}
