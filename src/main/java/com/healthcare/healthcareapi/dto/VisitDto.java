package com.healthcare.healthcareapi.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class VisitDto {

    private Long id;

    private Instant start;

    private Instant end;

    private Long patientId;

    private Long doctorId;
}
