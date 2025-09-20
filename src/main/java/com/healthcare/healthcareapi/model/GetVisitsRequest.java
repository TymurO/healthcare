package com.healthcare.healthcareapi.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetVisitsRequest {

    /**
     * Page number
     */
    private Integer page;

    /**
     * Number of patients per page
     */
    private Integer size;

    /**
     * Search string to filter by patient's name
     */
    private String search;

    /**
     * List of doctors which visits to show
     */
    private List<Integer> doctorIds;
}
