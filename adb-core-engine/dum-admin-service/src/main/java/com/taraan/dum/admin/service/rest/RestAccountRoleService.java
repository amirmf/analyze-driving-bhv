package com.taraan.dum.admin.service.rest;

import com.taraan.dum.common.TokenUtils;
import com.taraan.dum.dto.account.AccountRoleDto;
import com.taraan.dum.service.assembler.AccountRoleAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Path("/accountRole")
@Component
public class RestAccountRoleService {
    @Autowired
    private AccountRoleAssembler accountRoleAssembler;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@Context HttpHeaders headers, @PathParam("id") Long id) {
        /*List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        */
        return Response.ok().entity(accountRoleAssembler.getAccountRole(id)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAccountRole(@Context HttpHeaders headers, AccountRoleDto accountDto) throws IOException {
        /*List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        */
        return Response.ok().entity(accountRoleAssembler.createAccountRole(accountDto)).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAccountRole(@Context HttpHeaders headers, AccountRoleDto accountDto) throws IOException {
        /*List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        */
        return Response.ok().entity(accountRoleAssembler.updateAccountRole(accountDto)).build();
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccount(@Context HttpHeaders headers, @QueryParam("code") String code, @QueryParam("name") String name, @QueryParam("from") Long from, @QueryParam("size") Long size) {
        /*List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        */
        return Response.ok().entity(accountRoleAssembler.getAccountRoles(name, code, from, size)).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeAccountRole(@Context HttpHeaders headers, @PathParam("id") Long id) {
        /*List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        */
        accountRoleAssembler.removeAccountRole(id);
        return Response.ok().build();
    }
}
