package com.taraan.dum.service.rest.exceptions;

import com.taraan.dum.common.InvalidTokenException;
import com.taraan.dum.dto.exception.ExceptionMessage;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

@Component
public class InvalidTokenExceptionMapper implements ExceptionMapper<InvalidTokenException> {
    @Override
    public Response toResponse(InvalidTokenException throwable) {
        throwable.printStackTrace();
        return Response.status(404).entity(new ExceptionMessage("Invalid Token."))
                .type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}
