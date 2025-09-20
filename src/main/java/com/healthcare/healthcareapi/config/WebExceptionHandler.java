package com.healthcare.healthcareapi.config;

import com.healthcare.healthcareapi.exception.ValidationException;
import com.healthcare.healthcareapi.model.ValidationProblem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.healthcare.healthcareapi.exception.ValidationException.ofMessage;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@ControllerAdvice
public class WebExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<ValidationProblem> validationException(final ValidationException ex) {
        log.error(ex.getMessage(), ex);

        final var validationProblem = ex.getValidationProblem();

        return ResponseEntity
                .status(BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(validationProblem);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ValidationProblem> illegalArgumentException(final IllegalArgumentException ex) {
        log.error(ex.getMessage(), ex);

        final var validationProblem = ofMessage(ex.getMessage()).getValidationProblem();

        return ResponseEntity
                .status(BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(validationProblem);
    }
}
