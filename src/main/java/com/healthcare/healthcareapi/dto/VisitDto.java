package com.healthcare.healthcareapi.dto;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class VisitDto {

    private Long id;

    private ZonedDateTime start;

    private ZonedDateTime end;

    private Long patientId;

    private Long doctorId;
}
