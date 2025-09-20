package com.healthcare.healthcareapi.mapper;

import com.healthcare.healthcareapi.dto.VisitDto;
import com.healthcare.healthcareapi.entity.Patient;
import com.healthcare.healthcareapi.entity.Visit;
import com.healthcare.healthcareapi.model.PatientInfo;
import com.healthcare.healthcareapi.model.VisitInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface VisitMapper {

    @Mapping(target = "id", source = "visit.id")
    @Mapping(target = "start", expression = "java(visit.getStartDateTime().atZone(zoneId))")
    @Mapping(target = "end", expression = "java(visit.getEndDateTime().atZone(zoneId))")
    @Mapping(target = "patientId", source = "visit.patient.id")
    @Mapping(target = "doctorId", source = "visit.doctor.id")
    VisitDto toVisitDto(Visit visit, ZoneId zoneId);

    default List<PatientInfo> toPatientInfo(Map<Patient, List<Visit>> patientToVisits) {
        return patientToVisits.entrySet().stream()
                .map(this::toPatientInfo)
                .toList();
    }

    @Mapping(target = "firstName", source = "key.firstName")
    @Mapping(target = "lastName", source = "key.lastName")
    @Mapping(target = "lastVisits", source = "value")
    PatientInfo toPatientInfo(Map.Entry<Patient, List<Visit>> patientToVisit);

    @Mapping(target = "start", expression = "java(visit.getStartDateTime().atZone(ZoneId.of(visit.getDoctor().getTimezone())))")
    @Mapping(target = "end", expression = "java(visit.getEndDateTime().atZone(ZoneId.of(visit.getDoctor().getTimezone())))")
    @Mapping(target = "doctor.firstName", source = "doctor.firstName")
    @Mapping(target = "doctor.lastName", source = "doctor.lastName")
    @Mapping(target = "doctor.totalPatients", source = "doctor.totalPatients")
    VisitInfo toVisitInfo(Visit visit);
}
