package com.taraan.dum.service.rest.exceptions;

import com.taraan.dum.dto.exception.ExceptionMessage;
import com.taraan.dum.exceptions.UserAlreadyJoinException;
import com.taraan.dum.exceptions.reward.InsufficientBadgeException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class UserAlreadyJoinExceptionMapper implements ExceptionMapper<UserAlreadyJoinException> {
    @Override
    public Response toResponse(UserAlreadyJoinException throwable) {
        throwable.printStackTrace();
        return Response.status(511).entity(new ExceptionMessage("User Already Join."))
                .type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}
