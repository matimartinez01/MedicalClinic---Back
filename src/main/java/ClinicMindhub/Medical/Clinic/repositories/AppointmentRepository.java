package ClinicMindhub.Medical.Clinic.repositories;

import ClinicMindhub.Medical.Clinic.models.Appointment;
import ClinicMindhub.Medical.Clinic.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Boolean existsByDoctorAndDateAndTime (Doctor doctor, LocalDate date, Integer time);

}
