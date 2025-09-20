package com.healthcare.healthcareapi.controller;

import com.healthcare.healthcareapi.dto.VisitDto;
import com.healthcare.healthcareapi.model.CreateVisitRequest;
import com.healthcare.healthcareapi.model.GetVisitsResponse;
import com.healthcare.healthcareapi.service.VisitService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class VisitControllerTest {

    @Mock
    private VisitService visitService;

    @InjectMocks
    private VisitController visitController;

    @Test
    @DisplayName("GIVEN create visit request " +
            "WHEN create visit " +
            "THEN should successfully create visit")
    void createVisitTest() {
        // given
        var createVisitRequest = CreateVisitRequest.builder()
                .doctorId(1)
                .patientId(1)
                .start("2025-09-20T13:00:00")
                .end("2025-09-20T13:30:00")
                .build();

        given(visitService.createVisit(createVisitRequest)).willReturn(VisitDto.builder().build());

        // when
        var actual = visitController.createVisit(createVisitRequest);

        // then
        assertThat(actual).isEqualTo(VisitDto.builder().build());
    }

    @Test
    @DisplayName("GIVEN get visits request " +
            "WHEN get visits " +
            "THEN should successfully get visits")
    void getVisitsTest() {
        // given
        given(visitService.getVisits(any())).willReturn(GetVisitsResponse.builder().build());

        // when
        var actual = visitController.getVisits(1, 10, Optional.empty(), Optional.empty());

        // then
        assertThat(actual).isEqualTo(GetVisitsResponse.builder().build());
    }
}
