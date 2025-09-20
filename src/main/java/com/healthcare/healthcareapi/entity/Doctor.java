package com.healthcare.healthcareapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Doctor")
@Data
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String timezone;

    // Gets number of patients that had visited the doctor
    @Formula("(select count(distinct v.patient_id) from visit v where v.doctor_id = id)")
    private int totalPatients;
}
