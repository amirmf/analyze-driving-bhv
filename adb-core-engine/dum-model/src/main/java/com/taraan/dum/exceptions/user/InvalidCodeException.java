package com.taraan.dum.exceptions.user;

import com.taraan.dum.exceptions.DumException;

public class InvalidCodeException extends DumException {
    public InvalidCodeException() {
        super();
    }

    public InvalidCodeException(String message) {
        super(message);
    }

    public InvalidCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCodeException(Throwable cause) {
        super(cause);
    }

    public InvalidCodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
