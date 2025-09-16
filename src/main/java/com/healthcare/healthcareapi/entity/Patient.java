package com.healthcare.healthcareapi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Patients")
@Data
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
}
