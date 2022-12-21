package com.taraan.dum.admin.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taraan.dum.common.TokenUtils;
import com.taraan.dum.dto.badge.BadgeDto;
import com.taraan.dum.dto.badge.BadgePage;
import com.taraan.dum.service.assembler.BadgeAssembler;
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

@Path("/badge")
@Component
public class RestBadgeService {

    @Autowired
    private BadgeAssembler badgeAssembler;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBadge(@Context HttpHeaders headers, BadgeDto badgeDto) throws IOException {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));

        badgeAssembler.createBadge(badgeDto);
        return Response.ok().build();
    }

    @POST
    @Path("/uploadIcon")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBadgePicture(@Context HttpHeaders headers,
                                       @FormDataParam("icon") InputStream uploadedInputStream,
                                       @FormDataParam("icon") FormDataContentDisposition fileDetail,
                                       @FormDataParam("icon") FormDataBodyPart bodyPart,
                                       @FormDataParam("id") Long id) throws IOException {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
//        ObjectMapper mapper = new ObjectMapper();
        badgeAssembler.updateBadgePicture(uploadedInputStream, id);
        return Response.ok().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBadge(@Context HttpHeaders headers, BadgeDto badgeDto) {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        badgeAssembler.updateBadge(badgeDto);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBadge(@Context HttpHeaders headers, @PathParam("id") Long id) {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        badgeAssembler.deleteBadge(id);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBadge(@Context HttpHeaders headers, @PathParam("id") Long id) {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        final BadgeDto badge = badgeAssembler.getBadge(id);
        return Response.ok(badge).build();
    }

    @GET
    @Path("/list")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBadges(@Context HttpHeaders headers,
                              @QueryParam("all") boolean all,
                              @QueryParam("name") String name,
                              @QueryParam("code") String code,
                              @QueryParam("from") Long from,
                              @QueryParam("size") Long size) {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        final BadgePage badges = badgeAssembler.getBadges(all,name,code, from, size);
        return Response.ok(badges).build();
    }

}
