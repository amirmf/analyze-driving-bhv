package com.taraan.dum.service.rest.exceptions;

import com.taraan.dum.dto.exception.ExceptionMessage;
import com.taraan.dum.exceptions.EntityDoesNotExistException;
import com.taraan.dum.exceptions.reward.InsufficientBadgeException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class EntityDoesNotExistExceptionMapper implements ExceptionMapper<EntityDoesNotExistException> {
    @Override
    public Response toResponse(EntityDoesNotExistException throwable) {
        throwable.printStackTrace();
        return Response.status(511).entity(new ExceptionMessage("Entity Does Not Exist : " + throwable.getMessage()))
                .type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}
