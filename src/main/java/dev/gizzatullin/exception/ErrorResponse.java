package dev.gizzatullin.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private final String error;
    private final String stackTrace;

    public ErrorResponse(String error) {
        this.error = error;
        this.stackTrace = null;
    }

    public ErrorResponse(String error, String stackTrace) {
        this.error = error;
        this.stackTrace = stackTrace;
    }
}
