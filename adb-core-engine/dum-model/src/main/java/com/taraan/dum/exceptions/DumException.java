package com.taraan.dum.exceptions;

public class DumException extends RuntimeException {
    public DumException() {
    }

    public DumException(String message) {
        super(message);
    }

    public DumException(String message, Throwable cause) {
        super(message, cause);
    }

    public DumException(Throwable cause) {
        super(cause);
    }

    public DumException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
