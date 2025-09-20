package com.healthcare.healthcareapi.validation;

import com.healthcare.healthcareapi.exception.ValidationException;
import com.healthcare.healthcareapi.model.ValidationProblem;
import com.healthcare.healthcareapi.repository.VisitRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
public class VisitTimeValidatorTest {

    @Mock
    private VisitRepository visitRepository;

    @InjectMocks
    private VisitTimeValidator visitTimeValidator;

    private static final String START_AFTER_END_ERROR_MESSAGE = "Start date must not be after end date";
    private static final String DOCTOR_HAS_VISIT_ERROR_MESSAGE = "Doctor already has a visit at this time";

    @Test
    @DisplayName("GIVEN date that is already taken " +
            "WHEN validate " +
            "THEN should throw validation error")
    void validateVisitTimeWhenDateIsTaken() {
        // given
        Instant start  = Instant.now();
        Instant end  = start.plusSeconds(1800);
        var doctorId = 1L;

        given(visitRepository.existsByDoctorIdAndTimeOverlap(doctorId, start, end)).willReturn(true);

        // when + then
        ValidationException ex = assertThrows(
                ValidationException.class,
                () -> visitTimeValidator.validate(start, end, doctorId)
        );

        assertThat(ex.getValidationProblem()).isEqualTo(ValidationProblem.builder()
                        .type("VALIDATION")
                        .message(DOCTOR_HAS_VISIT_ERROR_MESSAGE)
                .build());
    }

    @Test
    @DisplayName("GIVEN start after end " +
            "WHEN validate " +
            "THEN should throw validation error")
    void validateVisitTimeWhenStartAfterEnd() {
        // given
        Instant start  = Instant.now();
        Instant end  = start.minusSeconds(1800);
        var doctorId = 1L;

        // when + then
        ValidationException ex = assertThrows(
                ValidationException.class,
                () -> visitTimeValidator.validate(start, end, doctorId)
        );

        assertThat(ex.getValidationProblem()).isEqualTo(ValidationProblem.builder()
                .type("VALIDATION")
                .message(START_AFTER_END_ERROR_MESSAGE)
                .build());
        verifyNoInteractions(visitRepository);
    }

    @Test
    @DisplayName("GIVEN valid date " +
            "WHEN validate " +
            "THEN should not throw any error")
    void validateVisitTimeWhenValidDate() {
        // given
        Instant start  = Instant.now();
        Instant end  = start.plusSeconds(1800);
        var doctorId = 1L;

        given(visitRepository.existsByDoctorIdAndTimeOverlap(doctorId, start, end)).willReturn(false);

        // when + then
        assertDoesNotThrow(() -> visitTimeValidator.validate(start, end, doctorId));
    }
}
