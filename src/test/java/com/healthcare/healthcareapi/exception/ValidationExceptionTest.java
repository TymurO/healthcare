package com.healthcare.healthcareapi.exception;

import com.healthcare.healthcareapi.model.ValidationProblem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidationExceptionTest {

    @Test
    @DisplayName("GIVEN validation error message " +
            "WHEN get validation error " +
            "THEN should generate validation exception with that message")
    void getValidationErrorMessage() {
        // given
        final var message = "Some error";

        // when
        ValidationException validationException = ValidationException.ofMessage(message);

        // then
        assertThat(validationException.getValidationProblem()).isEqualTo(ValidationProblem.builder()
                .type("VALIDATION")
                .message(message)
                .build());
    }
}
