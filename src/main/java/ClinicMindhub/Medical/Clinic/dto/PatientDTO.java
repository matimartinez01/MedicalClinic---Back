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

    public PatientDTO(Patient patient) {
        this.id = patient.getId();
        this.firstName = patient.getFirstName();
        this.lastName = patient.getLastName();
        this.genre = patient.getGenre();
        this.birthDate = patient.getBirthDate();
        for (Appointment appointment : patient.getAppointments()) {
            this.appointments.add(new AppointmentDTO(appointment));
        }
    }
}
