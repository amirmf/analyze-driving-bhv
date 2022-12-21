package com.taraan.dum.admin.service.rest;

import com.taraan.dum.common.TokenUtils;
import com.taraan.dum.dto.AnalyzeFormulaDto;
import com.taraan.dum.dto.AnalyzeFormulaPage;
import com.taraan.dum.service.assembler.AnalyzeFormulaAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/analyzeFormula")
@Component
public class RestAnalyzeFormulaService {

    @Autowired
    private AnalyzeFormulaAssembler analyzeFormulaAssembler;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAnalyzeFormula(@Context HttpHeaders headers, AnalyzeFormulaDto analyzeFormulaDto) {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        return Response.ok().entity(analyzeFormulaAssembler.createAnalyzeFormula(analyzeFormulaDto)).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAnalyzeFormula(@Context HttpHeaders headers, AnalyzeFormulaDto analyzeFormulaDto) {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        ;
        return Response.ok().entity(analyzeFormulaAssembler.updateAnalyzeFormula(analyzeFormulaDto)).build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAnalyzeFormula(@Context HttpHeaders headers, @PathParam("id") Long id) {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        analyzeFormulaAssembler.deleteAnalyzeFormula(id);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAnalyzeFormula(@Context HttpHeaders headers, @PathParam("id") Long id) {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        final AnalyzeFormulaDto analyzeFormula = analyzeFormulaAssembler.getAnalyzeFormula(id);
        return Response.ok(analyzeFormula).build();
    }

    @GET
    @Path("/list")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAnalyzeFormulas(@Context HttpHeaders headers,
                                       @QueryParam("name") String name,
                                       @QueryParam("formulaEvent") String formulaEvent,
                                       @QueryParam("formulaType") String formulaType,
                                       @QueryParam("from") Long from,
                                       @QueryParam("size") Long size) {
//        List<String> authentication = headers.getRequestHeader("authentication");
//        Long userId = TokenUtils.getId(authentication.get(0));
        final AnalyzeFormulaPage analyzeFormulas = analyzeFormulaAssembler.getAnalyzeFormulas(name, formulaEvent, formulaType, from, size);
        return Response.ok(analyzeFormulas).build();
    }

}
