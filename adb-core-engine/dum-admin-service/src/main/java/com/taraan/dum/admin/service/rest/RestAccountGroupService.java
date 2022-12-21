package com.taraan.dum.admin.service.rest;

import com.taraan.dum.common.TokenUtils;
import com.taraan.dum.dto.account.AccountGroupDto;
import com.taraan.dum.dto.account.ActionAccountRoleRequest;
import com.taraan.dum.service.assembler.AccountGroupAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Path("/accountGroup")
@Component
public class RestAccountGroupService {
    @Autowired
    private AccountGroupAssembler accountGroupAssembler;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@Context HttpHeaders headers, @PathParam("id") Long id) {
/*
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
*/
        return Response.ok().entity(accountGroupAssembler.getAccountGroup(id)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAccountGroup(@Context HttpHeaders headers, AccountGroupDto accountDto) throws IOException {
/*
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
*/
        return Response.ok().entity(accountGroupAssembler.createAccountGroup(accountDto)).build();
    }

    @POST
    @Path("/accountRoles")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAccountGroup(@Context HttpHeaders headers, ActionAccountRoleRequest request) throws IOException {
        accountGroupAssembler.addRoleToAccountGroup(request);
        return Response.ok().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAccountGroup(@Context HttpHeaders headers, AccountGroupDto accountDto) throws IOException {
/*
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
*/
        return Response.ok().entity(accountGroupAssembler.updateAccountGroup(accountDto)).build();
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccount(@Context HttpHeaders headers, @QueryParam("name") String name, @QueryParam("code") String code, @QueryParam("from") Long from, @QueryParam("size") Long size) {
/*
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
*/
        return Response.ok().entity(accountGroupAssembler.getAccountGroups(name, code, from, size)).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeAccountGroup(@Context HttpHeaders headers, @PathParam("id") Long id) {
/*
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
*/
        accountGroupAssembler.removeAccountGroup(id);
        return Response.ok().build();
    }
}
