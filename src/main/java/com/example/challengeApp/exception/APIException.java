package com.example.challengeApp.exception;

import java.io.Serial;

public class APIException extends RuntimeException {
    @Serial
    private static final Long serialVersionUID = 1L;

    public APIException() {
    }

    public APIException(String message) {
        super(message);
    }
}
