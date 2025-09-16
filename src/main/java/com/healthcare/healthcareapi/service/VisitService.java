package com.healthcare.healthcareapi.service;

import com.healthcare.healthcareapi.dto.VisitDto;
import com.healthcare.healthcareapi.entity.Visit;
import com.healthcare.healthcareapi.mapper.VisitMapper;
import com.healthcare.healthcareapi.model.CreateVisitRequest;
import com.healthcare.healthcareapi.repository.DoctorRepository;
import com.healthcare.healthcareapi.repository.PatientRepository;
import com.healthcare.healthcareapi.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final VisitMapper visitMapper;

    public VisitDto createVisit(CreateVisitRequest createVisitRequest) {
        Instant start = Instant.parse(createVisitRequest.getStart());
        Instant end = Instant.parse(createVisitRequest.getEnd());

        final var visit = visitRepository.save(Visit.builder()
                .startDateTime(start)
                .endDateTime(end)
                .patient(patientRepository.getReferenceById(createVisitRequest.getPatientId().longValue()))
                .doctor(doctorRepository.getReferenceById(createVisitRequest.getDoctorId().longValue()))
                .build());
        return visitMapper.toVisitDto(visit);
    }
}
