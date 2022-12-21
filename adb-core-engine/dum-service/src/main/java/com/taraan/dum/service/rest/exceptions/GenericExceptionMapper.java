package com.taraan.dum.service.rest.exceptions;

import com.taraan.dum.dto.exception.ExceptionMessage;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

@Component
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable throwable) {
        throwable.printStackTrace();
        return Response.status(520).entity(new ExceptionMessage("Internal Error"))
                .type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}
