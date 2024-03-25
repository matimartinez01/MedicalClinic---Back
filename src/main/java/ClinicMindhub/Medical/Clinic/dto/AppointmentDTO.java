package ClinicMindhub.Medical.Clinic.dto;

import ClinicMindhub.Medical.Clinic.models.Appointment;

import java.time.LocalDate;

public class AppointmentDTO {

    private Long id;
    private LocalDate date;
    private Integer time;
    private DoctorDTO doctor;
    private PatientDTO patient;

    public AppointmentDTO(Appointment appointment) {
        this.id = appointment.getId();
        this.date = appointment.getDate();
        this.time = appointment.getTime();
        this.doctor = new DoctorDTO(appointment.getDoctor());
        this.patient = new PatientDTO(appointment.getPatient());
    }
}
