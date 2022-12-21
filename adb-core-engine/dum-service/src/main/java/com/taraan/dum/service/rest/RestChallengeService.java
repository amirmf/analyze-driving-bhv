package com.taraan.dum.service.rest;

import com.taraan.dum.common.TokenUtils;
import com.taraan.dum.dto.challenge.ChallengeDto;
import com.taraan.dum.dto.challenge.ChallengePage;
import com.taraan.dum.dto.challenge.JoinChallengeRequest;
import com.taraan.dum.service.assembler.ChallengeAssembler;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/challenge")
public class RestChallengeService {

    @Autowired
    private ChallengeAssembler challengeAssembler;

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getChallenges(@Context HttpHeaders headers) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        ChallengePage challengePage = challengeAssembler.getChallenges(userId);
        return Response.ok().entity(challengePage).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response joinChallenge(@Context HttpHeaders headers, JoinChallengeRequest joinChallengeRequest) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        challengeAssembler.joinChallenge(joinChallengeRequest.getChallengeId(),userId);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getChallenge(@Context HttpHeaders headers, @PathParam("id") Long id) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        ChallengeDto challengePage = challengeAssembler.getChallenge(userId, id);
        return Response.ok().entity(challengePage).build();
    }


}
