package dev.gizzatullin.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneralException(final Exception e) {
        log.error("Unexpected error occurred: ", e);
        return new ErrorResponse(e.getMessage(), Arrays.toString(e.getStackTrace()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEntityNotFoundException(EntityNotFoundException e) {
        log.warn("Entity not found: {}", e.getMessage());
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final ConstraintViolationException e) {
        log.warn("Validation error: ", e);
        return new ErrorResponse(e.getMessage(), Arrays.toString(e.getStackTrace()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.warn("Validation error: ", e);
        return new ErrorResponse(e.getFieldError().getDefaultMessage(), Arrays.toString(e.getStackTrace()));
    }

//    // Можно добавить другие обработчики, например, для конфликтов
//    @ExceptionHandler(SpecificConflictException.class)
//    @ResponseStatus(HttpStatus.CONFLICT)
//    public ErrorResponse handleConflictException(final SpecificConflictException e) {
//        log.warn("Conflict error: ", e);
//        return new ErrorResponse(e.getMessage(), Arrays.toString(e.getStackTrace()));
//    }
}
