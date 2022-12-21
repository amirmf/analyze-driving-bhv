package com.taraan.dum.analyzer.rest;

import com.taraan.dum.analyzer.assembler.AnalyzeAssembler;
import com.taraan.dum.dto.analyze.AnalyzeTripRequest;
import com.taraan.dum.dto.analyze.AnalyzeTripResult;
import com.taraan.dum.dto.analyze.JmsAnalyzeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/analyze")
@Component
public class RestAnalyzeService {
    @Autowired
    private AnalyzeAssembler analyzeAssembler;

    @POST
    @Path("/parseFormula")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response parseFormula(AnalyzeTripRequest request)
    {
        AnalyzeTripResult analyzeTripResult = analyzeAssembler.parseFormula(request);
        return Response.accepted().entity(analyzeTripResult).build();
    }
    @POST
    @Path("/adminAnalyze")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response adminAnalyze(JmsAnalyzeRequest request)
    {
        analyzeAssembler.analyzeData(request);
        return Response.accepted().entity("ok").build();
    }
}
