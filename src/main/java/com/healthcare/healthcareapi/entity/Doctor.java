package com.healthcare.healthcareapi.entity;

import jakarta.persistence.*;
import lombok.Data;

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
}
