package com.example.newsapi.exception;

public class ExistsException extends RuntimeException{
    public ExistsException() {
        super();
    }

    public ExistsException(String message) {
        super(message);
    }

    public ExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistsException(Throwable cause) {
        super(cause);
    }

    protected ExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
