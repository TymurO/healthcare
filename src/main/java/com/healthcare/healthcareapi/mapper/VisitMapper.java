package com.healthcare.healthcareapi.mapper;

import com.healthcare.healthcareapi.dto.VisitDto;
import com.healthcare.healthcareapi.entity.Visit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface VisitMapper {

    @Mapping(target = "start", source = "startDateTime")
    @Mapping(target = "end", source = "endDateTime")
    @Mapping(target = "patientId", expression = "java(visit.getPatient().getId())")
    @Mapping(target = "doctorId", expression = "java(visit.getDoctor().getId())")
    VisitDto toVisitDto(Visit visit);
}
