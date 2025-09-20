package com.healthcare.healthcareapi.exception;

import com.healthcare.healthcareapi.model.ValidationProblem;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class ValidationException extends RuntimeException {

    private final ValidationProblem validationProblem;

    public ValidationException(ValidationProblem validationProblem) {
        super(validationProblem.getMessage());
        this.validationProblem = validationProblem;
    }

    public static ValidationException ofMessage(String message) {
        var problem = ValidationProblem.builder()
                .type("VALIDATION")
                .message(message)
                .build();

        return new ValidationException(problem);
    }
}
