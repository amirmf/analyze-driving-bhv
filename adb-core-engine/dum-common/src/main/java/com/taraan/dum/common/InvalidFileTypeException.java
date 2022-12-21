package com.taraan.dum.common;

public class InvalidFileTypeException extends RuntimeException {

    public InvalidFileTypeException() {
    }

    public InvalidFileTypeException(String message) {
        super(message);
    }

    public InvalidFileTypeException(Throwable cause) {
        super(cause);
    }
}
