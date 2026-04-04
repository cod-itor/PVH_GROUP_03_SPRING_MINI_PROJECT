package org.ksga.pvh_group_03_spring_mini_project.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException(NotFoundException ex) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        detail.setStatus(HttpStatus.NOT_FOUND);
        detail.setDetail(ex.getMessage());
        detail.setProperties(Map.of("Timestamp", LocalDateTime.now()));

        return detail;
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ProblemDetail UserAlreadyExistException(UserAlreadyExistException ex) {

        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setDetail(ex.getMessage());
        detail.setProperty("Timestamp", LocalDateTime.now());

        return detail;
    }

    @ExceptionHandler(HabitLogStatusException.class)
    public ProblemDetail HabitLogStatusException(HabitLogStatusException ex) {

        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setDetail(ex.getMessage());
        detail.setProperty("Timestamp", LocalDateTime.now());

        return detail;
    }


    @ExceptionHandler(NotYetVerifiedException.class)
    public ProblemDetail NotYetVerifiedException(NotYetVerifiedException ex) {

        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setDetail(ex.getMessage());
        detail.setProperty("Timestamp", LocalDateTime.now());

        return detail;
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ProblemDetail InvalidTokenException(InvalidTokenException ex) {

        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        detail.setProperty("error", "UNAUTHORIZED");
        detail.setDetail(ex.getMessage());
        detail.setProperty("Timestamp", LocalDateTime.now());

        return detail;
    }


    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail RuntimeException(RuntimeException ex) {

        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setDetail(ex.getMessage());
        detail.setProperty("Timestamp", LocalDateTime.now());

        return detail;
    }

    @ExceptionHandler(OTPValidationException.class)
    public ProblemDetail OTPValidationException(OTPValidationException ex) {

        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setDetail(ex.getMessage());
        detail.setProperty("Timestamp", LocalDateTime.now());

        return detail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problemDetail.setType(URI.create("about:blank"));
        problemDetail.setTitle("Bad Request");
        problemDetail.setStatus(HttpStatus.BAD_REQUEST.value());
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        Map<String, String> errors = new LinkedHashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        problemDetail.setProperty("errors", errors);
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ProblemDetail handleMethodValidationException(HandlerMethodValidationException e) {
        Map<String, String> errors = new HashMap<>();

        // Loop through each invalid parameter validation result
        e.getParameterValidationResults().forEach(parameterError -> {
            String paramName = parameterError.getMethodParameter().getParameterName(); // Get parameter name

            // Loop through each validation error message for this parameter
            for (var messageError : parameterError.getResolvableErrors()) {
                errors.put(paramName, messageError.getDefaultMessage()); // Store error message
            }
        });

        // Create structured ProblemDetail response
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Method Parameter Validation Failed");
        problemDetail.setProperties(Map.of(
                "timestamp", LocalDateTime.now(),
                "errors", errors // Attach validation errors
        ));

        return problemDetail;
    }

}
