package com.taraan.dum.admin.service.rest;


import com.taraan.dum.common.TokenUtils;
import com.taraan.dum.dto.reward.*;
import com.taraan.dum.dto.statistic.RewardPage;
import com.taraan.dum.service.assembler.RewardAssembler;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@Path("/reward")
public class RestRewardService {
    @Autowired
    public RewardAssembler rewardAssembler;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createReward(@Context HttpHeaders headers, CreateRewardRequest request) throws IOException {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        rewardAssembler.createReward(request);
        return Response.ok().build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateReward(@Context HttpHeaders headers, UpdateRewardRequest request) throws IOException {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        rewardAssembler.updateReward(request);
        return Response.ok().build();
    }

    @POST
    @Path("/uploadImage")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upoadImage(@Context HttpHeaders headers,
                               @FormDataParam("image") InputStream inputStream,
                               @FormDataParam("image") FormDataContentDisposition fileDetail,
                               @FormDataParam("image") FormDataBodyPart bodyPart,
                               @FormDataParam("id") Long id) throws IOException {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        rewardAssembler.uploadImage(inputStream, id);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getRewards(@Context HttpHeaders headers, @PathParam("id") Long id) {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        final RewardDto reward = rewardAssembler.getReward(id);
        return Response.ok().entity(reward).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteReward(@Context HttpHeaders headers, @PathParam("id") Long id) {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        rewardAssembler.deleteReward(id);
        return Response.ok().build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/removeBadgeRequired")
    public Response removeBadgeRequired(@Context HttpHeaders headers, RemoveBadgeRequiredRequest request) throws IOException {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        rewardAssembler.removeBadgeRequired(request);
        return Response.ok().build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/addBadgeRequired")
    public Response addBadgeRequired(@Context HttpHeaders headers, AddBadgeRequiredRequest request) throws IOException {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        rewardAssembler.addBadgeRequired(request);
        return Response.ok().build();
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getRewards(@Context HttpHeaders headers,
                               @QueryParam("type") String type,
                               @QueryParam("name") String name,
                               @QueryParam("rewardState") String rewardState,
                               @QueryParam("size") Long size,
                               @QueryParam("from") Long from) {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        final com.taraan.dum.dto.reward.RewardPage rewards = rewardAssembler.getRewards(type, name, rewardState, from, size);
        return Response.ok().entity(rewards).build();
    }

}
