package ClinicMindhub.Medical.Clinic.dto;

import ClinicMindhub.Medical.Clinic.models.Appointment;
import ClinicMindhub.Medical.Clinic.models.Genre;
import ClinicMindhub.Medical.Clinic.models.Patient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientDTO {

    private Long id;
    private String firstName, lastName;
    private Genre genre;
    private LocalDate birthDate;
    private List<AppointmentDTO> appointments = new ArrayList<>();
    private String email;

    public PatientDTO(Patient patient) {
        this.id = patient.getId();
        this.firstName = patient.getFirstName();
        this.lastName = patient.getLastName();
        this.genre = patient.getGenre();
        this.birthDate = patient.getBirthDate();
        this.appointments = patient.getAppointments().stream().map(AppointmentDTO::new).toList();
        this.email = patient.getEmail();
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Genre getGenre() {
        return genre;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public List<AppointmentDTO> getAppointments() {
        return appointments;
    }

    public String getEmail() {
        return email;
    }
}
