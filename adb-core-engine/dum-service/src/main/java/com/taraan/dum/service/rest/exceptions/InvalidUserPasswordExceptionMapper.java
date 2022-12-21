package com.taraan.dum.service.rest.exceptions;

import com.taraan.dum.dto.exception.ExceptionMessage;
import com.taraan.dum.exceptions.user.InvalidUserPasswordException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class InvalidUserPasswordExceptionMapper implements ExceptionMapper<InvalidUserPasswordException> {
    @Override
    public Response toResponse(InvalidUserPasswordException throwable) {
        return Response.status(404).entity(new ExceptionMessage("Invalid User Or Password."))
                .type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}
