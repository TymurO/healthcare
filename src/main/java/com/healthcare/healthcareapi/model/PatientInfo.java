package com.healthcare.healthcareapi.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PatientInfo {

    private String firstName;

    private String lastName;

    /**
     * All last patient's visits
     */
    private List<VisitInfo> lastVisits;
}
