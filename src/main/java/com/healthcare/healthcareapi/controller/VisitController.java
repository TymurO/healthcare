package com.healthcare.healthcareapi.controller;

import com.healthcare.healthcareapi.dto.VisitDto;
import com.healthcare.healthcareapi.model.CreateVisitRequest;
import com.healthcare.healthcareapi.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/visit")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;

    @PostMapping("")
    public VisitDto createVisit(@NonNull @RequestBody CreateVisitRequest createVisitRequest) {
        return visitService.createVisit(createVisitRequest);
    }
}
