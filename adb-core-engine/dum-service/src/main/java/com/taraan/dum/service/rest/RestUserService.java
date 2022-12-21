package com.taraan.dum.service.rest;

import com.taraan.dum.common.TokenUtils;
import com.taraan.dum.dto.user.*;
import com.taraan.dum.service.assembler.UserAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
@Component
public class RestUserService {
    @Autowired
    private UserAssembler userAssembler;

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(RegisterUserRequest registerUserRequest) {
        String password = userAssembler.registerUser(registerUserRequest);
        return Response.ok().entity(new RegisterUserResponse(password)).build();
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest loginRequest) {
        LoginResponse response = userAssembler.login(loginRequest, false);
        return Response.ok().entity(response).build();
    }

    @POST
    @Path("/notificationToken")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response notificationToken(@Context HttpHeaders headers, NotificationRequest notificationRequest) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        notificationRequest.setUserId(userId);
        userAssembler.updateNotificationToken(notificationRequest);
        return Response.ok().build();
    }

    @POST
    @Path("/profile")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProfile(@Context HttpHeaders headers, ProfileUserDto request) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        userAssembler.updateProfile(userId, request);
        return Response.ok().build();
    }

    @GET
    @Path("/profile")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getProfile(@Context HttpHeaders headers) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        final ProfileUserDto profile = userAssembler.getProfile(userId);
        return Response.ok().entity(profile).build();
    }

    @GET
    @Path("/isCompleteProfile")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response isCompleteProfile(@Context HttpHeaders headers) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        Boolean result = userAssembler.isCompleteProfile(userId);
        return Response.ok().entity(new IsCompleteProfileResponse(result)).build();
    }


    @POST
    @Path("/forgetPassword")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response forgetPassword(RegisterUserRequest loginRequest) {
        userAssembler.forgetPassword(loginRequest);
        return Response.ok().build();
    }

    @POST
    @Path("/resetPassword")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response forgetPassword(ResetPasswordRequest resetPasswordRequest) {
        userAssembler.resetPassword(resetPasswordRequest);
        return Response.ok().build();
    }
}
