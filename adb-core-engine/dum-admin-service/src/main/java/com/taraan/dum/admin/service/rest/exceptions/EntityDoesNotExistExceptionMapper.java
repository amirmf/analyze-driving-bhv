package com.taraan.dum.admin.service.rest.exceptions;

import com.taraan.dum.exceptions.EntityDoesNotExistException;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

@Component
public class EntityDoesNotExistExceptionMapper implements ExceptionMapper<EntityDoesNotExistException> {
    @Override
    public Response toResponse(EntityDoesNotExistException throwable) {
        throwable.printStackTrace();
        return Response.status(522).entity("EntityDoesNotExistException" + throwable.getMessage())
                .type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}
