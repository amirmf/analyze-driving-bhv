package com.taraan.dum.service.rest;

import com.taraan.dum.common.TokenUtils;
import com.taraan.dum.dto.badge.BadgeDto;
import com.taraan.dum.dto.badge.BadgePage;
import com.taraan.dum.dto.group.*;
import com.taraan.dum.service.assembler.BadgeAssembler;
import com.taraan.dum.service.assembler.GroupAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/group")
@Component
public class RestGroupService {

    @Autowired
    private GroupAssembler groupAssembler;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createGroupDriving(@Context HttpHeaders headers, CreateGroupDrivingRequest groupDto) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        groupAssembler.createGroup(groupDto, userId);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteGroupDriving(@Context HttpHeaders headers, @PathParam("id") Long groupId) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        groupAssembler.deleteGroup(groupId, userId);
        return Response.ok().build();
    }

    @POST
    @Path("/removeMember")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteGroupDriving(@Context HttpHeaders headers, RemoveMemberDto removeMemberDto) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        groupAssembler.removeMember(userId, removeMemberDto.getGroupId(), removeMemberDto.getMemberId());
        return Response.ok().build();
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/multipleInvite")
    public Response multipleInvite(@Context HttpHeaders headers, GroupMultipleInviteRequest groupInviteRequest) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        groupAssembler.invites(groupInviteRequest, userId);
        return Response.ok().build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/invite")
    public Response invite(@Context HttpHeaders headers, GroupInviteRequest groupInviteRequest) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        groupAssembler.invite(groupInviteRequest, userId);
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/inviteList/{pageNumber}")
    public Response inviteList(@Context HttpHeaders headers, @PathParam("pageNumber") Long pageNumber) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        final GroupInvitePage groupInvites = groupAssembler.getGroupInvites(userId, pageNumber);
        return Response.ok().entity(groupInvites).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/rejectInvite/{inviteId}")
    public Response rejectInvite(@Context HttpHeaders headers, @PathParam("inviteId") Long inviteId) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        groupAssembler.rejectInvite(inviteId);
        return Response.ok().build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/acceptInvite/{inviteId}")
    public Response acceptInvite(@Context HttpHeaders headers, @PathParam("inviteId") Long inviteId) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        groupAssembler.acceptInvite(userId, inviteId);
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/groupData/{groupId}")
    public Response getGroupData(@Context HttpHeaders headers, @PathParam("groupId") Long groupId) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        GroupInfoPage groupPage = groupAssembler.getGroupData(groupId, userId);
        return Response.ok().entity(groupPage).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/list/{pageId}")
    public Response getGroupList(@Context HttpHeaders headers, @PathParam("pageId") Long groupId) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        GroupPage groupPage = groupAssembler.getGroups(userId, groupId);
        return Response.ok().entity(groupPage).build();
    }


}
