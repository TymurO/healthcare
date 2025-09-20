package com.healthcare.healthcareapi.repository;

import com.healthcare.healthcareapi.entity.Patient;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    /**
     * Gets list of patient ids with certain firstName that have visits
     *
     * @param firstName firstName of patient
     * @param pageable pagination
     * @return list of patient ids
     */
    @Query("""
        select p.id from Patient p
        where (:firstName is null or lower(p.firstName) = lower(:firstName))
        and exists(select 1 from Visit v where v.patient.id = p.id)
    """)
    Slice<Long> searchPatientsByFirstName(String firstName, Pageable pageable);
}
