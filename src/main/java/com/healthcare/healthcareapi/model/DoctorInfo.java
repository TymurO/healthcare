package com.healthcare.healthcareapi.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorInfo {

    private String firstName;

    private String lastName;

    private Integer totalPatients;
}
