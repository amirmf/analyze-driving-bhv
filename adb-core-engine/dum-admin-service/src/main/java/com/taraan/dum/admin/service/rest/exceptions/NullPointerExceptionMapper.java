package com.taraan.dum.admin.service.rest.exceptions;

import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

@Component
public class NullPointerExceptionMapper implements ExceptionMapper<NullPointerException> {
    @Override
    public Response toResponse(NullPointerException throwable) {
        throwable.printStackTrace();
        return Response.status(521).entity("NullPointerException" + throwable.getMessage())
                .type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}
