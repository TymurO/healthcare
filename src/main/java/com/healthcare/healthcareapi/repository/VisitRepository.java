package com.healthcare.healthcareapi.repository;

import com.healthcare.healthcareapi.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    /**
     * Gets list of visits for patients and doctors
     *
     * @param patientIds patient ids
     * @param doctorIds doctor ids
     * @return list of {@link Visit} visits
     */
    default List<Visit> searchVisitsByPatientIdsAndDoctorIds(List<Long> patientIds, List<Long> doctorIds) {
        if (doctorIds.isEmpty()) {
            return this.searchVisitsByPatientIdIn(patientIds);
        } else {
            return this.searchVisitsByPatientIdInAndDoctorIdIn(patientIds, doctorIds);
        }
    }

    /**
     * gets list of visits for patients
     *
     * @param patientIds patient ids
     * @return list of {@link Visit} visits
     */
    @Query("""
        select v from Visit v
        where v.patient.id in :patientIds
    """)
    List<Visit> searchVisitsByPatientIdIn(List<Long> patientIds);

    /**
     * Search visit for certain patients and doctors
     *
     * @param patientIds list of patients
     * @param doctorIds list of doctors
     * @return list of {@link Visit} visit between given patients and doctors
     */
    @Query("""
        select v from Visit v
        where v.patient.id in :patientIds
        and v.doctor.id in :doctorIds
    """)
    List<Visit> searchVisitsByPatientIdInAndDoctorIdIn(List<Long> patientIds, List<Long> doctorIds);

    /**
     * Checks if the doctor has visit in hte time interval
     *
     * @param doctorId doctor's id
     * @param start start of visit
     * @param end end of visit
     * @return if there is visit in the time interval
     */
    @Query("""
        select case when count(v) > 0 then true else false end
        from Visit v
        where v.doctor.id = :doctorId
            and (
                    v.startDateTime < :end and v.endDateTime > :start
                )
    """)
    boolean existsByDoctorIdAndTimeOverlap(
            @Param("doctorId") Long doctorId,
            @Param("start") Instant start,
            @Param("end") Instant end
    );
}
