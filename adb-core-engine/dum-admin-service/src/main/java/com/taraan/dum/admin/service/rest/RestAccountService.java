package com.taraan.dum.admin.service.rest;

import com.taraan.dum.common.TokenUtils;
import com.taraan.dum.dto.account.AccountDto;
import com.taraan.dum.dto.account.AcountLoginRequest;
import com.taraan.dum.dto.account.ActionAccountGroupRequest;
import com.taraan.dum.dto.account.ActionAccountRoleRequest;
import com.taraan.dum.dto.user.LoginRequest;
import com.taraan.dum.service.assembler.AccountAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Path("/account")
@Component
public class RestAccountService {
    @Autowired
    private AccountAssembler accountAssembler;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@Context HttpHeaders headers, @PathParam("id") Long id) {
        /*List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        */
        return Response.ok().entity(accountAssembler.getAccount(id)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAccount(@Context HttpHeaders headers, AccountDto accountDto) throws IOException {
        /*List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        */
        return Response.ok().entity(accountAssembler.createAccount(accountDto)).build();
    }

    @POST
    @Path("/accountGroups")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addGroupToAccount(@Context HttpHeaders headers, ActionAccountGroupRequest accountDto) throws IOException {
        /*List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        */
        accountAssembler.addGroupToAccount(accountDto);
        return Response.ok().build();
    }
    @POST
    @Path("/accountRoles")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRoleToAccount(@Context HttpHeaders headers, ActionAccountRoleRequest accountDto) throws IOException {
        /*List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        */
        accountAssembler.addRoleToAccount(accountDto);
        return Response.ok().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAccount(@Context HttpHeaders headers, AccountDto accountDto) throws IOException {
        /*List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        */
        return Response.ok().entity(accountAssembler.updateAccount(accountDto)).build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response Login(@Context HttpHeaders headers, AcountLoginRequest loginRequest) throws IOException {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        return Response.ok().entity(accountAssembler.login(loginRequest)).build();
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccount(@Context HttpHeaders headers, @QueryParam("username") String username, @QueryParam("isEnabled") Boolean enabled, @QueryParam("from") Long from, @QueryParam("size") Long size) {
        /*List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        */
        return Response.ok().entity(accountAssembler.getAccounts(username, enabled, from, size)).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeAccount(@Context HttpHeaders headers, @PathParam("id") Long id) {
        /*List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        */
        accountAssembler.removeAccount(id);
        return Response.ok().build();
    }
}
