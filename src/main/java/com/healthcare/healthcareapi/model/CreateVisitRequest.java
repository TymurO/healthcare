package com.healthcare.healthcareapi.model;

import lombok.Getter;

@Getter
public class CreateVisitRequest {

    private String start;

    private String end;

    private Integer patientId;

    private Integer doctorId;
}
