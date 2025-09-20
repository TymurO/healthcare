package com.healthcare.healthcareapi.validation;

import com.healthcare.healthcareapi.exception.ValidationException;
import com.healthcare.healthcareapi.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

import static com.healthcare.healthcareapi.exception.ValidationException.ofMessage;

@Component
@RequiredArgsConstructor
public class VisitTimeValidator {

    private final VisitRepository visitRepository;

    private static final String START_AFTER_END_ERROR_MESSAGE = "Start date must not be after end date";
    private static final String DOCTOR_HAS_VISIT_ERROR_MESSAGE = "Doctor already has a visit at this time";

    public void validate(Instant start, Instant end, Long doctorId) throws ValidationException {
        if (start.isAfter(end)) {
            throw ofMessage(START_AFTER_END_ERROR_MESSAGE);
        }

        boolean exists = visitRepository.existsByDoctorIdAndTimeOverlap(
                doctorId,
                start,
                end
        );

        if (exists) {
            throw ofMessage(DOCTOR_HAS_VISIT_ERROR_MESSAGE);
        }
    }
}
