package com.taraan.dum.admin.service.rest.exceptions;

import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

@Component
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable throwable) {
        throwable.printStackTrace();
        return Response.status(520).entity("Internal Error")
                .type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}
