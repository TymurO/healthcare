package com.healthcare.healthcareapi.controller;

import com.healthcare.healthcareapi.dto.VisitDto;
import com.healthcare.healthcareapi.model.CreateVisitRequest;
import com.healthcare.healthcareapi.model.GetVisitsRequest;
import com.healthcare.healthcareapi.model.GetVisitsResponse;
import com.healthcare.healthcareapi.service.VisitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/visit")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;

    @PostMapping("")
    public VisitDto createVisit(@Valid @RequestBody CreateVisitRequest createVisitRequest) {
        return visitService.createVisit(createVisitRequest);
    }

    @GetMapping("")
    public GetVisitsResponse getVisits(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size,
                                       @RequestParam Optional<String> search, @RequestParam Optional<List<Integer>> doctorIds) {
        final var request = GetVisitsRequest.builder()
                .page(page)
                .size(size)
                .doctorIds(doctorIds.orElse(Collections.emptyList()))
                .search(search.orElse(null))
                .build();

        return visitService.getVisits(request);
    }
}
