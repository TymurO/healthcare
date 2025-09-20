package com.healthcare.healthcareapi.mapper;

import com.healthcare.healthcareapi.dto.VisitDto;
import com.healthcare.healthcareapi.entity.Doctor;
import com.healthcare.healthcareapi.entity.Patient;
import com.healthcare.healthcareapi.entity.Visit;
import com.healthcare.healthcareapi.model.DoctorInfo;
import com.healthcare.healthcareapi.model.PatientInfo;
import com.healthcare.healthcareapi.model.VisitInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class VisitMapperTest {

    private final VisitMapper visitMapper = Mappers.getMapper(VisitMapper.class);

    @Test
    @DisplayName("GIVEN visit and zone id " +
            "WHEN map to visit dto " +
            "THEN should successfully map")
    void mapTiVisitDtoTest() {
        // given
        var timezone = ZoneId.of("Europe/Paris"); // GMT+2

        var visit = Visit.builder()
                .id(1L)
                .startDateTime(Instant.parse("2025-09-20T10:30:00Z"))
                .endDateTime(Instant.parse("2025-09-20T11:30:00Z"))
                .doctor(Doctor.builder().id(1L).build())
                .patient(Patient.builder().id(1L).build())
                .build();

        var expectedVisitDto = VisitDto.builder()
                .id(1L)
                .doctorId(1L)
                .patientId(1L)
                .start(ZonedDateTime.of(2025, 9, 20, 12, 30, 0, 0, timezone))
                .end(ZonedDateTime.of(2025, 9, 20, 13, 30, 0, 0, timezone))
                .build();

        // when
        var visitDto = visitMapper.toVisitDto(visit, timezone);

        // then
        assertThat(visitDto).isEqualTo(expectedVisitDto);
    }

    @Test
    @DisplayName("")
    void mapToPatientInfoTest() {
        // given
        var timezone = ZoneId.of("Europe/Paris");
        var firstPatient = Patient.builder().firstName("Sam").lastName("Brouly").id(1L).build();
        var secondPatient = Patient.builder().firstName("Michel").lastName("Brouny").id(2L).build();
        var doctor = Doctor.builder().id(1L).firstName("Tom").lastName("Heil").totalPatients(2)
                .timezone(timezone.toString())
                .build();

        List<Visit> firstVisits = List.of(
                Visit.builder()
                        .id(1L)
                        .startDateTime(Instant.parse("2025-09-20T10:30:00Z"))
                        .endDateTime(Instant.parse("2025-09-20T11:30:00Z"))
                        .doctor(doctor)
                        .patient(firstPatient)
                        .build(),
                Visit.builder()
                        .id(2L)
                        .startDateTime(Instant.parse("2025-09-21T10:30:00Z"))
                        .endDateTime(Instant.parse("2025-09-21T11:30:00Z"))
                        .doctor(doctor)
                        .patient(firstPatient)
                        .build()
        );

        List<Visit> secondVisits = List.of(
                Visit.builder()
                        .id(3L)
                        .startDateTime(Instant.parse("2025-09-20T10:30:00Z"))
                        .endDateTime(Instant.parse("2025-09-20T11:30:00Z"))
                        .doctor(doctor)
                        .patient(secondPatient)
                        .build(),
                Visit.builder()
                        .id(4L)
                        .startDateTime(Instant.parse("2025-09-21T10:30:00Z"))
                        .endDateTime(Instant.parse("2025-09-21T11:30:00Z"))
                        .doctor(doctor)
                        .patient(secondPatient)
                        .build()
        );

        List<PatientInfo> expectedPatientInfos = List.of(
                PatientInfo.builder()
                        .firstName("Sam")
                        .lastName("Brouly")
                        .lastVisits(List.of(
                                VisitInfo.builder()
                                        .start(ZonedDateTime.of(2025, 9, 20, 12, 30, 0, 0, timezone))
                                        .end(ZonedDateTime.of(2025, 9, 20, 13, 30, 0, 0, timezone))
                                        .doctor(DoctorInfo.builder()
                                                .firstName("Tom")
                                                .lastName("Heil")
                                                .totalPatients(2)
                                                .build())
                                        .build(),
                                VisitInfo.builder()
                                        .start(ZonedDateTime.of(2025, 9, 21, 12, 30, 0, 0, timezone))
                                        .end(ZonedDateTime.of(2025, 9, 21, 13, 30, 0, 0, timezone))
                                        .doctor(DoctorInfo.builder()
                                                .firstName("Tom")
                                                .lastName("Heil")
                                                .totalPatients(2)
                                                .build())
                                        .build()
                        ))
                        .build(),
                PatientInfo.builder()
                        .firstName("Michel")
                        .lastName("Brouny")
                        .lastVisits(List.of(
                                VisitInfo.builder()
                                        .start(ZonedDateTime.of(2025, 9, 20, 12, 30, 0, 0, timezone))
                                        .end(ZonedDateTime.of(2025, 9, 20, 13, 30, 0, 0, timezone))
                                        .doctor(DoctorInfo.builder()
                                                .firstName("Tom")
                                                .lastName("Heil")
                                                .totalPatients(2)
                                                .build())
                                        .build(),
                                VisitInfo.builder()
                                        .start(ZonedDateTime.of(2025, 9, 21, 12, 30, 0, 0, timezone))
                                        .end(ZonedDateTime.of(2025, 9, 21, 13, 30, 0, 0, timezone))
                                        .doctor(DoctorInfo.builder()
                                                .firstName("Tom")
                                                .lastName("Heil")
                                                .totalPatients(2)
                                                .build())
                                        .build()
                        ))
                        .build()
        );

        // when
        List<PatientInfo> actual = visitMapper.toPatientInfo(Map.of(
                firstPatient, firstVisits,
                secondPatient, secondVisits
        ));

        // then
        assertThat(actual).isEqualTo(expectedPatientInfos);
    }
}
