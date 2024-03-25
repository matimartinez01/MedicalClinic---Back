package ClinicMindhub.Medical.Clinic.dto;

import ClinicMindhub.Medical.Clinic.models.Appointment;
import ClinicMindhub.Medical.Clinic.models.Patient;

import java.time.LocalDate;

public class AppointmentDTO {

    private Long id;
    private LocalDate date;
    private Integer time;
    private String doctor;
    private String patient;

    public AppointmentDTO(Appointment appointment) {
        this.id = appointment.getId();
        this.date = appointment.getDate();
        this.time = appointment.getTime();
        this.doctor = appointment.getDoctor().getFirstName() + " " + appointment.getDoctor().getLastName();
        this.patient = appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName();
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getTime() {
        return time;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getPatient() {
        return patient;
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                '}';
    }
}
