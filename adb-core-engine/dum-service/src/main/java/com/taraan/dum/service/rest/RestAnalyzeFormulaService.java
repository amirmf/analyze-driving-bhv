package com.taraan.dum.service.rest;

import com.taraan.dum.dto.AnalyzeFormulaDto;
import com.taraan.dum.dto.AnalyzeFormulaPage;
import com.taraan.dum.service.assembler.AnalyzeFormulaAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
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
    public Response createAnalyzeFormula(AnalyzeFormulaDto analyzeFormulaDto) {
        analyzeFormulaAssembler.createAnalyzeFormula(analyzeFormulaDto);
        return Response.ok().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAnalyzeFormula(AnalyzeFormulaDto analyzeFormulaDto) {
        analyzeFormulaAssembler.updateAnalyzeFormula(analyzeFormulaDto);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAnalyzeFormula(@PathParam("id") Long id) {
        analyzeFormulaAssembler.deleteAnalyzeFormula(id);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAnalyzeFormula(@PathParam("id") Long id) {
        final AnalyzeFormulaDto analyzeFormula = analyzeFormulaAssembler.getAnalyzeFormula(id);
        return Response.ok(analyzeFormula).build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAnalyzeFormulas(@QueryParam("from")Long from,@QueryParam("size") Long size) {
        final AnalyzeFormulaPage analyzeFormulas = analyzeFormulaAssembler.getAnalyzeFormulas(null,null,null,from,size);
        return Response.ok(analyzeFormulas).build();
    }

}
