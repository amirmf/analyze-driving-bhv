package com.taraan.dum.service.rest;

import com.taraan.dum.dto.badge.BadgeDto;
import com.taraan.dum.dto.badge.BadgePage;
import com.taraan.dum.service.assembler.BadgeAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/badge")
@Component
public class RestBadgeService {

    @Autowired
    private BadgeAssembler badgeAssembler;


    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBadge(@PathParam("id") Long id) {
        badgeAssembler.deleteBadge(id);
        return Response.ok().build();
    }
    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBadge(@PathParam("id") Long id) {
        final BadgeDto badge = badgeAssembler.getBadge(id);
        return Response.ok(badge).build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBadges() {
        final BadgePage badges = badgeAssembler.getBadges();
        return Response.ok(badges).build();
    }

}
