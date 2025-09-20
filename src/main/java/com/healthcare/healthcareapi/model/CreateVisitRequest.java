package com.healthcare.healthcareapi.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateVisitRequest {

    /**
     * Start of visit in doctor's timezone
     */
    @NotNull
    private String start;

    /**
     * End of visit in doctor's timezone
     */
    @NotNull
    private String end;

    @NotNull
    private Integer patientId;

    @NotNull
    private Integer doctorId;
}
