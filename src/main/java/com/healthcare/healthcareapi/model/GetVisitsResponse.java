package com.healthcare.healthcareapi.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetVisitsResponse {

    /**
     * Number of patients meet the requirements
     */
    private Integer count;

    /**
     * Visits for each patient
     */
    private List<PatientInfo> data;
}
