package com.taraan.dum.admin.service.rest;


import com.taraan.dum.common.TokenUtils;
import com.taraan.dum.dto.challenge.*;
import com.taraan.dum.service.assembler.ChallengeAssembler;
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

@Path("/challenge")
@Component
public class RestChallengeService {
    @Autowired
    private ChallengeAssembler challengeAssembler;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createChallenge(@Context HttpHeaders headers, CreateChallengeRequest createChallengeRequest) throws IOException {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        Long id = challengeAssembler.createChallenge(createChallengeRequest);
        return Response.ok().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateChallenge(@Context HttpHeaders headers, CreateChallengeRequest createChallengeRequest) throws IOException {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        Long id = challengeAssembler.updateChallenge(createChallengeRequest);
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/uploadIcon")
    public Response uploadIcon(@Context HttpHeaders headers, @FormDataParam("icon") InputStream uploadedInputStream,
                               @FormDataParam("icon") FormDataContentDisposition fileDetail,
                               @FormDataParam("icon") FormDataBodyPart bodyPart,
                               @FormDataParam("id") Long id) throws IOException {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        challengeAssembler.uploadIcon(uploadedInputStream, id);
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/uploadImage")
    public Response uploadImage(@Context HttpHeaders headers, @FormDataParam("image") InputStream uploadedInputStream,
                                @FormDataParam("image") FormDataContentDisposition fileDetail,
                                @FormDataParam("image") FormDataBodyPart bodyPart,
                                @FormDataParam("id") Long id) throws IOException {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        challengeAssembler.uploadImage(uploadedInputStream, id);
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/uploadPrizeImage")
    public Response uploadPrizeImage(@Context HttpHeaders headers, @FormDataParam("image") InputStream uploadedInputStream,
                                     @FormDataParam("image") FormDataContentDisposition fileDetail,
                                     @FormDataParam("image") FormDataBodyPart bodyPart,
                                     @FormDataParam("id") Long id) throws IOException {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        challengeAssembler.uploadPrizeImage(uploadedInputStream, id);
        return Response.ok().build();
    }

    @POST
    @Path("/addTeam")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTeamToChallenge(@Context HttpHeaders headers, AddTeamToChallengeRequest request) throws IOException {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        challengeAssembler.addTeamToChallenge(request);
        return Response.ok().build();
    }

    @POST
    @Path("/removeTeam")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeTeam(@Context HttpHeaders headers, RemoveTeamFromChallengeRequest request) throws IOException {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        challengeAssembler.removeTeamFromChallenge(request);
        return Response.ok().build();
    }

    @GET
    @Path("/list")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getChallenges(@Context HttpHeaders headers,
                                  @QueryParam("name") String name,
                                  @QueryParam("fromStartDate") String fromStartDate,
                                  @QueryParam("toStartDate") String toStartDate,
                                  @QueryParam("fromEndDate") String fromEndDate,
                                  @QueryParam("toEndDate") String toEndDate,
                                  @QueryParam("from") Long from,
                                  @QueryParam("size") Long size) {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        final AdminChallengePage challenges = challengeAssembler.getChallenges(name, fromStartDate, toStartDate, fromEndDate, toEndDate, from, size);
        return Response.ok().entity(challenges).build();
    }

    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeTeam(@Context HttpHeaders headers, @PathParam("id") Long id) throws IOException {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        final AdminChallengeDto challenge = challengeAssembler.getChallenge(id);
        return Response.ok().entity(challenge).build();
    }
}
