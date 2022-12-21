package com.taraan.dum.exceptions.user;

import com.taraan.dum.exceptions.DumException;

public class InvalidUserPasswordException extends DumException {
    public InvalidUserPasswordException() {
        super();
    }

    public InvalidUserPasswordException(String message) {
        super(message);
    }

    public InvalidUserPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUserPasswordException(Throwable cause) {
        super(cause);
    }

    public InvalidUserPasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
