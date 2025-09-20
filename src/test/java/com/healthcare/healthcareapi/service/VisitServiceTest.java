package com.healthcare.healthcareapi.service;

import com.healthcare.healthcareapi.dto.VisitDto;
import com.healthcare.healthcareapi.entity.Doctor;
import com.healthcare.healthcareapi.entity.Patient;
import com.healthcare.healthcareapi.entity.Visit;
import com.healthcare.healthcareapi.mapper.VisitMapper;
import com.healthcare.healthcareapi.model.CreateVisitRequest;
import com.healthcare.healthcareapi.model.GetVisitsRequest;
import com.healthcare.healthcareapi.model.GetVisitsResponse;
import com.healthcare.healthcareapi.model.PatientInfo;
import com.healthcare.healthcareapi.repository.DoctorRepository;
import com.healthcare.healthcareapi.repository.PatientRepository;
import com.healthcare.healthcareapi.repository.VisitRepository;
import com.healthcare.healthcareapi.validation.VisitTimeValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;

import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class VisitServiceTest {

    @Mock
    private VisitRepository visitRepository;
    @Mock
    private DoctorRepository doctorRepository;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private VisitMapper visitMapper;
    @Mock
    private VisitTimeValidator visitTimeValidator;

    @InjectMocks
    private VisitService visitService;

    @Test
    @DisplayName("GIVEN all valid data " +
            "WHEN create visit " +
            "THEN should successfully create visit")
    void testCreateVisit() {
        // given
        var createVisitRequest = CreateVisitRequest.builder()
                .start("2025-09-20T13:00:00")
                .end("2025-09-20T13:30:00")
                .patientId(1)
                .doctorId(1)
                .build();
        var timezone = ZoneId.of("Europe/Paris");

        given(doctorRepository.findById(1L)).willReturn(Optional.of(Doctor.builder()
                .id(1L)
                .timezone(timezone.toString())
                .build()));
        given(patientRepository.getReferenceById(1L)).willReturn(Patient.builder().id(1L).build());
        given(visitRepository.save(any())).willReturn(Visit.builder().build());
        given(visitMapper.toVisitDto(any(), eq(timezone))).willReturn(VisitDto.builder().build());

        // when
        var actual = visitService.createVisit(createVisitRequest);

        // then
        assertThat(actual).isEqualTo(VisitDto.builder().build());
    }

    @Test
    @DisplayName("GIVEN get visits request " +
            "WHEN visit exists " +
            "THEN should successfully return visits")
    void testGetVisits() {
        // given
        var getVisitsRequest = GetVisitsRequest.builder()
                .page(1)
                .size(10)
                .search("Sam")
                .doctorIds(Collections.emptyList())
                .build();
        var pageRequest = PageRequest.of(0, 10);
        var patient = Patient.builder().id(1L).build();
        var patientInfo = PatientInfo.builder().build();

        given(patientRepository.searchPatientsByFirstName("Sam", pageRequest))
                .willReturn(new SliceImpl<>(List.of(1L), pageRequest, false));
        given(visitRepository.searchVisitsByPatientIdsAndDoctorIds(List.of(1L), Collections.emptyList()))
                .willReturn(List.of(Visit.builder()
                        .id(1L)
                        .patient(patient)
                        .build()));
        given(visitMapper.toPatientInfo(Map.of(
                patient, List.of(Visit.builder().patient(patient).id(1L).build())
        ))).willReturn(List.of(patientInfo));

        // when
        var actual = visitService.getVisits(getVisitsRequest);

        // then
        assertThat(actual).isEqualTo(GetVisitsResponse.builder()
                .data(List.of(patientInfo))
                .count(1)
                .build());
    }
}
