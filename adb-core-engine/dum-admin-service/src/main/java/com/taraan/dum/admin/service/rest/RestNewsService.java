package com.taraan.dum.admin.service.rest;

import com.taraan.dum.common.TokenUtils;
import com.taraan.dum.dto.news.NewsDto;
import com.taraan.dum.service.assembler.NewsAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Path("/news")
@Component
public class RestNewsService {
    @Autowired
    private NewsAssembler newsAssembler;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@Context HttpHeaders headers, @PathParam("id") Long id) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        return Response.ok().entity(newsAssembler.getNews(id)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNews(@Context HttpHeaders headers, NewsDto newsDto) throws IOException {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        return Response.ok().entity(newsAssembler.createNews(newsDto)).build();
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNews(@Context HttpHeaders headers,@QueryParam("all") boolean all, @QueryParam("from") Long from, @QueryParam("size") Long size) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        return Response.ok().entity(newsAssembler.getNewses(all,from,size)).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeNews(@Context HttpHeaders headers, @PathParam("id") Long id) {
        List<String> authentication = headers.getRequestHeader("authentication");
        Long userId = TokenUtils.getId(authentication.get(0));
        newsAssembler.removeNews(id);
        return Response.ok().build();
    }
}
