package com.healthcare.healthcareapi.model;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class VisitInfo {

    /**
     * Start of visit in UTC
     */
    private ZonedDateTime start;

    /**
     * End of visit in UTC
     */
    private ZonedDateTime end;

    /**
     * Information about doctor
     */
    private DoctorInfo doctor;
}
