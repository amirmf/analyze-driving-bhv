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

@Path("/admin/users")
@Component
public class AdminService {
    @Autowired
    private UserAssembler userAssembler;

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(AdminRegisterUserRequest registerUserRequest) {
        String password = userAssembler.registerUser(registerUserRequest);
        return Response.ok().entity(new RegisterUserResponse(password)).build();
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest loginRequest) {
        LoginResponse response = userAssembler.login(loginRequest,true);
        return Response.ok().entity(response).build();
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getList(@Context HttpHeaders headers,
                            @QueryParam("phoneNumber") String phoneNumber,
                            @QueryParam("email") String email,
                            @QueryParam("firstName") String firstName,
                            @QueryParam("lastName") String lastName,
                            @QueryParam("from") Long from,
                            @QueryParam("size") Long size) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        UserPage userPage = userAssembler.getUserList(phoneNumber,email,firstName,lastName,from,size);
        return Response.ok().entity(userPage).build();
    }

    @POST
    @Path("/profile")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProfile(@Context HttpHeaders headers, ProfileUserDto request) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        userAssembler.updateProfile(request.getId(), request);
        return Response.ok().build();
    }

    @GET
    @Path("/profile/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getProfile(@Context HttpHeaders headers) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        final AdminProfileUserDto profile = userAssembler.getAdminProfile(userId);
        return Response.ok().entity(profile).build();
    }

}
