package com.healthcare.healthcareapi.service;

import com.healthcare.healthcareapi.dto.VisitDto;
import com.healthcare.healthcareapi.entity.Doctor;
import com.healthcare.healthcareapi.entity.Patient;
import com.healthcare.healthcareapi.entity.Visit;
import com.healthcare.healthcareapi.mapper.VisitMapper;
import com.healthcare.healthcareapi.model.*;
import com.healthcare.healthcareapi.repository.DoctorRepository;
import com.healthcare.healthcareapi.repository.PatientRepository;
import com.healthcare.healthcareapi.repository.VisitRepository;
import com.healthcare.healthcareapi.validation.VisitTimeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    private final VisitTimeValidator visitTimeValidator;
    private final VisitMapper visitMapper;

    public VisitDto createVisit(CreateVisitRequest createVisitRequest) {
        Doctor doctor = doctorRepository.findById(createVisitRequest.getDoctorId().longValue())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

        ZoneId zoneId = ZoneId.of(doctor.getTimezone());
        Instant start = LocalDateTime.parse(createVisitRequest.getStart())
                .atZone(zoneId).toInstant();
        Instant end = LocalDateTime.parse(createVisitRequest.getEnd())
                .atZone(zoneId).toInstant();

        visitTimeValidator.validate(start, end, doctor.getId());

        final var visit = visitRepository.save(Visit.builder()
                .startDateTime(start)
                .endDateTime(end)
                .patient(patientRepository.getReferenceById(createVisitRequest.getPatientId().longValue()))
                .doctor(doctor)
                .build());
        return visitMapper.toVisitDto(visit, zoneId);
    }

    public GetVisitsResponse getVisits(GetVisitsRequest getVisitsRequest) {
        List<Long> patientsToBeSearched = patientRepository.searchPatientsByFirstName(
                getVisitsRequest.getSearch(), PageRequest.of(getVisitsRequest.getPage() - 1, getVisitsRequest.getSize()))
                .getContent();

        List<Visit> patientsVisits = visitRepository.searchVisitsByPatientIdsAndDoctorIds(
                patientsToBeSearched,
                getVisitsRequest.getDoctorIds().stream().map(Integer::longValue).toList());

        // Creating map of patient to his visits in descending order
        Map<Patient, List<Visit>> patientToVisits = patientsVisits.stream()
                .collect(Collectors.groupingBy(Visit::getPatient)).entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .sorted(Comparator.comparing(Visit::getStartDateTime).reversed())
                                .toList()
                ));

        List<PatientInfo> patientInfos = visitMapper.toPatientInfo(patientToVisits);

        return GetVisitsResponse.builder()
                .data(patientInfos)
                .count(patientInfos.size())
                .build();
    }
}
