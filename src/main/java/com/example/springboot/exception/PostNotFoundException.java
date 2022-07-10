package com.example.springboot.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException() {
        super();
    }

    public PostNotFoundException(final String message) {
        super(message);
    }

    public PostNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PostNotFoundException(final Throwable cause) {
        super(cause);
    }

    protected PostNotFoundException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
