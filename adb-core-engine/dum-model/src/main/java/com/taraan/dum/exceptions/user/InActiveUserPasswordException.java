package com.taraan.dum.exceptions.user;

import com.taraan.dum.exceptions.DumException;

public class InActiveUserPasswordException extends DumException {
    public InActiveUserPasswordException() {
        super();
    }

    public InActiveUserPasswordException(String message) {
        super(message);
    }

    public InActiveUserPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public InActiveUserPasswordException(Throwable cause) {
        super(cause);
    }

    public InActiveUserPasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
